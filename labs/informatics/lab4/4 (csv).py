from main_parser import ParserJsonToDict


def dict_to_csv(d: dict, delimiter=',') -> str:
    """Из питоновского словаря в csv"""
    text_csv = ''
    for k, v in d.items():
        text_csv += f'{k}{delimiter}{str(v)}\n'
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
