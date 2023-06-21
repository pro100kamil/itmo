with open("sc123.txt", "w") as f:
    text = '''
add
Test1
74
22
999.0
HEAD_OF_DEPARTMENT


5

'''
    n = 50
    for i in range(n):
        print(f'''
add
Test{i}
74
22
999.0
HEAD_OF_DEPARTMENT


5

''', file=f, end='')
