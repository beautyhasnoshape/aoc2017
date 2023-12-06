with open("02.txt") as f:
    lines = [a.strip() for a in f.readlines()]

s = 0
for line in lines:
    s += (max([int(a) for a in line.split()]) - min([int(a) for a in line.split()]))

assert s == 47623


s = 0
for line in lines:
    found = False
    t = [int(a) for a in line.split()]
    for i in range(len(t)):
        for j in range(i + 1, len(t)):
            if max(t[i], t[j]) % min(t[i], t[j]) == 0:
                s += (max(t[i], t[j]) // min(t[i], t[j]))
                found = True
                break
        if found:
            break

print(s)
assert s == 312
