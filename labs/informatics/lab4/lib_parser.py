from json2xml import json2xml
from json2xml.utils import readfromjson


def main():
    fn_in, fn_out = 'in.json', 'out1.xml'
    with open(fn_out, 'w', encoding='utf-8') as f_out:
        data = readfromjson(fn_in)
        print(json2xml.Json2xml(data).to_xml(), file=f_out)


if __name__ == '__main__':
    main()
