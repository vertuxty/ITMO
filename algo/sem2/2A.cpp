#include <iostream>
#include <vector>
#include <math.h>
#include <climits>

//TASK: C
void build(std::vector<long long> &a, std::vector<std::pair<long long, std::pair<long long, long long>>> &t, int v, int l, int r) {
    if (l == r) {
        t[v].first = a[l];
        t[v].second = std::make_pair(l + 1, l + 1);
    }
    else {
        int m = (l + r) / 2;
        build(a, t, v * 2 + 1, l, m);
        build(a, t, 2 * v + 2, m + 1, r);
        t[v].first = std::max(t[2*v + 1].first, t[2*v + 2].first);
        t[v].second = std::make_pair(t[2*v + 1].second.first, t[2*v + 2].second.second);
    }
}


void push(std::vector<std::pair<long long, std::pair<long long, long long>>> &t, std::vector<long long> &add, int v) {
    t[2*v + 1].first = add[v]+t[2*v + 1].first;
    t[2*v + 2].first = add[v]+t[2*v + 2].first;
    add[2*v + 1] = add[v] + add[2*v + 1];
    add[2*v + 2] = add[v] + add[2*v + 2];
    add[v] = 0;
    // t[2*v + 1].first = add[v]^t[2*v + 1].first;
    // t[2*v + 2].first = add[v]^t[2*v + 2].first;
    // add[2*v + 1] = add[v] ^ add[2*v + 1];
    // add[2*v + 2] = add[v] ^ add[2*v + 2];
    // add[v] = 0;
}

void update(std::vector<std::pair<long long, std::pair<long long, long long>>> &t, std::vector<long long> &add, int v, int L, int R, int x) {
    if (L > t[v].second.second || R < t[v].second.first) {
        return;
    }
    if (L <= t[v].second.first && t[v].second.second <= R) {
        t[v].first = x + t[v].first;
        add[v] = add[v] + x;
        return;
    }
    push(t, add, v);
    update(t, add, 2*v + 1, L, R, x);
    update(t, add, 2*v + 2, L, R, x);
    t[v].first = std::max(t[2*v + 1].first, t[2*v + 2].first);
}

int find_max(std::vector<std::pair<long long, std::pair<long long, long long>>> &t,std::vector<long long> &add, int v, int L, int R) {
    int l = t[v].second.first; //Левая граница отрезка;
    int r = t[v].second.second; //Правая граница отрезка;
    // std::cout << "(" << l << ";" << r << ")" << L << " " << R << "\n";
    if (r < L || l > R) {
        return 0;
    }
    if (r <= R && l >= L) {
        return t[v].first;
    }
    push(t, add, v);
    int f1 = find_max(t, add, 2*v + 1, L, R);
    int f2 = find_max(t, add, 2*v + 2, L, R);
    return f1 > f2 ? f1 : f2;
}

int main() {
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);
    int n;
    std::cin >> n;
    int tmp = n;
    while ((n & (n - 1))!= 0) {
        n++;
    }
    std::vector<long long> a(n);
    std::vector<std::pair<long long, std::pair<long long, long long>>> t(2*n);
    std::vector<long long> add(2*n);
    for (int i = 0; i < n; i++) {
        if (i < tmp) {
            std::cin >> a[i];
        } else {
            a[i] = 0;
        }
    }
    build(a, t, 0, 0, n - 1);
    // for (std::pair<int, std::pair<int, int>> pos : t) {
	// 	std::cout << "(" << pos.first << "), {" << pos.second.first << ";" << pos.second.second <<"}  -;-  ";
	// }
    // std::cout << "\n";
    int m;
    std::cin >> m;
    char ch;
    int querr;
    for (int i = 0; i < m; i++) {
        std::cin >> ch;
        if (ch == 'm') {
            int l, r;
            std::cin >> l >> r;
            std::cout << find_max(t, add, 0, l , r) << " ";
        } else {
            int l, r, diff;
            std::cin >> l >> r >> diff;
            update(t, add, 0, l, r, diff);
        }
    }
}