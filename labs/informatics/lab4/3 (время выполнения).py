from time import time
import main_parser, lib_parser, re_parser


def timer(func, n):
    """За сколько времени функция func выполнится n раз"""
    st = time()
    for _ in range(n):
        func()
    return time() - st


if __name__ == '__main__':
    for name, pars in [('main_parser', main_parser), ('lib_parser', lib_parser),
                       ('re_parser', re_parser)]:
        print(f'{name}: {timer(pars.main, 100)} s')
