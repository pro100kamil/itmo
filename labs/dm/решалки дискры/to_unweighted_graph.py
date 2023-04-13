# если нужно преобразовать граф в незвешенный граф
# (сделать вес каждого ребра равным 1)
matrix = [[1 if c.isdigit() and c != '0'
           else 0 for c in line.split(';')]
          for line in open('graph.csv').read().strip().split('\n')]
for line in matrix:
    print(','.join(map(str, line)))
