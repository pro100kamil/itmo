class ParserJsonToDict:
    """Класс, содержащий функции для парсинга из json в словарь"""

    @staticmethod
    def error(text_error):
        print(f'ERROR: {text_error}')
        exit(0)

    @staticmethod
    def is_int(s: str) -> bool:
        return s.isdigit()

    @staticmethod
    def parse_int(s: str) -> int:
        return int(s)

    @staticmethod
    def is_float(s: str) -> bool:
        return s and set(s) <= (set(map(str, range(10))) | {'.'})

    @staticmethod
    def parse_float(s: str) -> float:
        return float(s)

    @staticmethod
    def is_bool(s: str) -> bool:
        return s in {'true', 'false'}

    @staticmethod
    def parse_bool(s: str) -> bool:
        return True if s == 'true' else False

    @staticmethod
    def is_null(s: str) -> bool:
        return s == 'null'

    @staticmethod
    def parse_null(s: str) -> None:
        return None

    @staticmethod
    def parse(s: str):
        # преобразуем строку в число, в булево значение или в None
        is_parse_d = {
            ParserJsonToDict.is_int: ParserJsonToDict.parse_int,
            ParserJsonToDict.is_float: ParserJsonToDict.parse_float,
            ParserJsonToDict.is_bool: ParserJsonToDict.parse_bool,
            ParserJsonToDict.is_null: ParserJsonToDict.parse_null
        }
        for f1, f2 in is_parse_d.items():
            if f1(s):
                return f2(s)
        ParserJsonToDict.error('неизвестный тип данных')

    @staticmethod
    def get_values(s: str, i: int) -> tuple:
        # ищет в строке s, начиная с i-той позиции
        while i < len(s):
            c = s[i]
            if c == '[':
                tmp = '['
                brackets = 1  # кол-во открывшихся - кол-во закрывшихся
                i += 1
                while brackets != 0:
                    if i >= len(s):
                        ParserJsonToDict.error('[ не закрывается')
                    if s[i] == '[':
                        brackets += 1
                    elif s[i] == ']':
                        brackets -= 1
                    tmp += s[i]
                    i += 1
                return ParserJsonToDict.parse_array(tmp), i
            elif c == '{':
                tmp = '{'
                brackets = 1  # кол-во открывшихся - кол-во закрывшихся
                i += 1
                while brackets != 0:
                    if i >= len(s):
                        ParserJsonToDict.error('{ не закрывается')
                    if s[i] == '{':
                        brackets += 1
                    elif s[i] == '}':
                        brackets -= 1
                    tmp += s[i]
                    i += 1
                return ParserJsonToDict.parse_json_object(tmp), i
            elif c.isalnum():
                tmp = ''
                while i < len(s) and (s[i].isalnum() or s[i] == '.'):
                    if i >= len(s):
                        ParserJsonToDict.error('строка без кавычек')
                    tmp += s[i]
                    i += 1
                return ParserJsonToDict.parse(tmp), i
            else:
                i += 1

    @staticmethod
    def parse_array(s: str) -> list:
        s = s.strip()[1:-1]
        res_l = []
        i = 0
        while i < len(s):
            c = s[i]
            if c == '"':
                tmp = ''
                i += 1
                while s[i] != '"':
                    if i >= len(s):
                        ParserJsonToDict.error('нет кавычек')
                    tmp += s[i]
                    i += 1
                res_l.append(tmp)
            elif c in '[{' or c.isalnum():
                value, i = ParserJsonToDict.get_values(s, i)
                res_l.append(value)
            else:
                i += 1

        return res_l

    @staticmethod
    def parse_json_object(s: str) -> dict:
        s = s.strip()[1:-1]
        res_d = {}
        key = None
        i = 0
        while i < len(s):
            c = s[i]
            if c == '"':
                tmp = ''
                i += 1
                while s[i] != '"':
                    if i >= len(s):
                        ParserJsonToDict.error('нет кавычек')
                    tmp += s[i]
                    i += 1
                i += 1
                if key is None:
                    key = tmp
                else:
                    res_d[key] = tmp
                    key = None
            elif c in '[{' or c.isalnum():
                if key is None:
                    ParserJsonToDict.error('отсутсвует ключ')
                value, i = ParserJsonToDict.get_values(s, i)
                res_d[key] = value
                key = None
            else:
                i += 1

        return res_d


def dict_to_xml(d, name='main'):
    """из питоновского словаря в xml"""
    text_xml = ''
    for k, v in d.items():
        if isinstance(v, dict):
            text_xml += dict_to_xml(v, k)
        elif isinstance(v, list):
            text_xml += f'<{k.replace(" ", "_")}>\n'
            for el in v:
                text_xml += dict_to_xml({'el_' + k: el}, '')
            text_xml += f'</{k.replace(" ", "_")}>\n'
        else:
            if ParserJsonToDict.is_float(k):
                k = 'num_' + k
            text_xml += f'<{k.replace(" ", "_")}>'
            text_xml += str(v)
            text_xml += f'</{k.replace(" ", "_")}>\n'
    if name == '':
        return text_xml
    return f'<{name.replace(" ", "_")}>\n' + text_xml + \
           f'</{name.replace(" ", "_")}>\n'


def main():
    with open('in.json', encoding='utf-8') as f_in, \
            open('out.xml', 'w', encoding='utf-8') as f_out:
        text = f_in.read()
        print(ParserJsonToDict.parse_json_object(text))
        print(dict_to_xml(ParserJsonToDict.parse_json_object(text)),
              file=f_out)


if __name__ == '__main__':
    main()
