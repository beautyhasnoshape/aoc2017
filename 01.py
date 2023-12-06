with open("01.txt") as f:
    lines = [a.strip() for a in f.readlines()]

s = 0
line = lines[0]
for i in range(len(line)):
    c1, c2 = int(line[i]), int(line[(i + 1) % len(line)])
    s += c1 if c1 == c2 else 0

print(s)
assert s == 1047


s = 0
line = lines[0]
for i in range(len(line)):
    c1, c2 = int(line[i]), int(line[(i + len(line) // 2) % len(line)])
    s += c1 if c1 == c2 else 0

print(s)
assert s == 1047
