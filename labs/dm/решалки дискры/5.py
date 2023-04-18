# всего лишь помогает сделать дз
# по двум графам, выводит информацию о степенях вершин
for fn in ['graph.csv', 'izo_graph.csv']:
    print(fn)
    matrix = [[int(c) if c.isdigit() and c != '0'
               else 0 for c in line.split(';')]
              for line in open(fn).read().strip().split('\n')]
    graph = {v + 1: {u + 1: matrix[v][u] for u in range(len(matrix[v]))}
             for v in range(len(matrix))}
    ps = {}
    for v in graph:
        p = 0
        for u in graph:
            if graph[v][u]:
                p += 1
        ps[v] = p
        print(f'v={v} p={p}')
    print('ps:', end=' ')
    print(*ps.values(), sep=', ')
    print(f'sum(ps)={sum(ps.values())}')
    for p in sorted(set(ps.values()), reverse=True):
        print(f'p={p}. вершины:', end=' ')
        print(*filter(lambda v: ps[v] == p, graph))
    print('-' * 30)

