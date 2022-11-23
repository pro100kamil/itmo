from main_parser import ParserJsonToDict


def universal_dict_to_csv(d: dict, delimiter=',') -> str:
    """Из питоновского словаря в csv"""
    text_csv = ''
    for k, v in d.items():
        text_csv += f'{k}{delimiter}{str(v).replace(",", ";")}\n'
    return text_csv


def parse(v) -> str:
    if isinstance(v, str):
        return f'"{v}"'
    return str(v).replace(',', ';')


def dict_to_csv(d: dict, delimiter=',') -> str:
    """Из питоновского словаря в csv"""
    text_csv = ''
    keys = ["Номер занятия"] + list(d["Занятие 1"].keys())
    text_csv += delimiter.join(map(parse, keys)) + '\n'
    for k, v in d.items():
        values = [k] + list(d[k].values())
        text_csv += delimiter.join(map(parse, values)) + '\n'
    # return text_csv.replace('[', '').replace(']', '')
    return text_csv


def main():
    fn_in, fn_out = 'in.json', 'out.csv'
    with open(fn_in, encoding='utf-8') as f_in, \
            open(fn_out, 'w', encoding='utf-8') as f_out:
        text = f_in.read()
        print(dict_to_csv(ParserJsonToDict.parse_json_object(text)),
              file=f_out)


if __name__ == '__main__':
    main()
