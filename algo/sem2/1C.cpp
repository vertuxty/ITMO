#include <iostream>
#include <vector>
#include <math.h>
#include <iostream>
#include <vector>
#include <math.h>


struct Node {
    int value;
    int pref;
    int suff;
    int count;
    int ans;
    Node() = default;
    Node (int x, int p, int s, int cnt, int a)
    {
        value = x;
        pref = p;
        suff = s;
        count = cnt;
        ans = a;
    }
};


Node combine(Node v1, Node v2)
{
    Node newNode = Node(0, 0, 0, 0, 0);
    if (v1.pref == v1.count)
    {
        newNode.pref = v1.pref + v2.pref;
    } else {
        newNode.pref = v1.pref;
    }
    if (v2.suff == v2.count)
    {
        newNode.suff = v1.suff + v2.suff;
    } else {
        newNode.suff = v2.suff;
    }
    newNode.count = v1.count + v2.count;
    newNode.ans = std::max(std::max(v1.suff + v2.pref, v1.ans), v2.ans);
    return newNode;
}

void build(const int a[], Node t[], int v, int l, int r) {
    if (l == r) {
        if (a[l] == 0)
        {
            t[v] = Node(a[l], 1, 1, 1, 1);
        } else {
            t[v] = Node(a[l], 0, 0, 1, 0);
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
        return {0, 0, 0, 1, 0};
    }
    if (L == l && r == R) {
        return t[v];
    }
    int m = (L + R)/2;
    return combine(query(t, v * 2, l, std::min(r, m), L, m), query(t, v * 2 + 1, std::max(l, m + 1), r, m + 1, R));
}

void update(Node t[], int v, int i, int x, int l, int r)
{
    if (l == r) {
        if (x == 0)
        {
            t[v] = Node(x, 1, 1, 1, 1);
        } else {
            t[v] = Node(x, 0, 0, 1, 0);
        }
    } else {
        int m = (l + r)/2;
        if (i <= m) {
            update(t, 2 * v, i, x, l, m);
        } else {
            update(t, 2 * v + 1, i, x, m + 1, r);
        }
        t[v] = combine(t[2 * v], t[2 * v + 1]);
    }
}

int main() {
    int n;
    std::cin >> n;
    int tmp = n;
    int a[n];
    Node t[4*n];

    for (int i = 0; i < n; i++) {
        std::cin >> a[i];
    }
    build(a, t, 1, 0, n - 1);
    int m;
    std::cin >> m;
    std::string query_;
    while (m > 0) {
        std::cin >> query_;
        if (query_ == "QUERY")
        {
            int l, r;
            std::cin >> l >> r;
            std::cout << query(t, 1, l - 1, r - 1, 0, n - 1).ans << "\n";
        } else {
            int i, x;
            std::cin >> i >> x;
            update(t, 1, i - 1, x, 0, n - 1);
        }
        m--;
    }
}