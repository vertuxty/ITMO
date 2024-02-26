#include <iostream>
#include <vector>


struct Node {
    int max;
    int close_out;
    int open_out;
    Node() = default;
    Node (int mx, int close, int open)
    {
        max = mx;
        close_out = close;
        open_out = open;
    }
};


Node combine(Node v1, Node v2)
{
    Node newNode = Node(0, 0, 0);
    int min = std::min(v1.open_out, v2.close_out);
    newNode.open_out = v1.open_out + v2.open_out - min;
    newNode.close_out = v1.close_out + v2.close_out - min;
    newNode.max = v1.max + v2.max + min;
    return newNode;
}

void build(const int a[], Node t[], int v, int l, int r) {
    if (l == r) {
        if (a[l] == 1)
        {
            t[v] = Node(0, 0, 1);
        } else {
            t[v] = Node(0, 1, 0);
        }
    }
    else {
        int m = (l + r) / 2;
        build(a, t, v * 2, l, m);
        build(a, t, 2 * v + 1, m + 1, r);
        t[v] = combine(t[2*v], t[2 * v + 1]);
    }
}

Node query(Node t[], int v, int l ,int r, int L, int R)
{
    if (l > r) {
        return {0, 0, 0};
    }
    if (L == l && r == R) {
        return t[v];
    }
    int m = (L + R)/2;
    return combine(query(t, v * 2, l, std::min(r, m), L, m), query(t, v * 2 + 1, std::max(l, m + 1), r, m + 1, R));
}
int main() {
    std::string str;
    std::cin >> str;
    int n = str.length();
    int a[n];
    Node t[4*n];
    for (int i = 0; i < n; i++) {
        if (str[i] == '(')
        {
            a[i] = 1;
        } else {
            a[i] = -1;
        }
    }
    build(a, t, 1, 0, n - 1);
    int m;
    std::cin >> m;
    while (m > 0) {
        int l, r;
        std::cin >> l >> r;
        std::cout << query(t, 1, l - 1, r - 1, 0, n - 1).max * 2 << "\n";
        m--;
    }
}