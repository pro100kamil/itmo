for s, fn in [('Обязательное задание', 'out.xml'),
              ('Дополнительное задание 1', 'out1.xml'),
              ('Дополнительное задание 2', 'out2.xml'),
              ('Дополнительное задание 3', 'out3.txt')
              ]:
    print(s)
    print(open(fn, encoding='utf-8').read())
