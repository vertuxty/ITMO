#include <iostream>
#include <vector>
#include <math.h>

void build(std::vector<int> &a, std::vector<std::pair<std::pair<int, int>, std::pair<int, int>>> &t, int v, int l, int r) {
	if (l == r) {
		t[v].first.first = a[l];
        t[v].first.second = 1;
        t[v].second = std::make_pair(l + 1, l + 1);
	}
	else {
		int m = (l + r) / 2;
		build(a, t, v * 2 + 1, l, m);
		build(a, t, 2 * v + 2, m + 1, r);
//		t[v].first = std::max(t[2*v + 1], t[2*v + 2]);
        if (t[2*v + 1].first.first > t[2*v + 2].first.first) {
            t[v].first = t[2*v + 1].first;
        } else if (t[2*v + 1].first.first < t[2*v + 2].first.first) {
            t[v].first = t[2*v + 2].first;
        } else {
            t[v].first.first = t[2*v + 1].first.first;
            t[v].first.second = t[2*v + 1].first.second + t[2*v + 2].first.second;
        }
        t[v].second = std::make_pair(t[2*v + 1].second.first, t[2*v + 2].second.second);
	}
}


std::pair<std::pair<int, int>, std::pair<int ,int>> query(std::vector<std::pair<std::pair<int, int>, std::pair<int, int>>> &t, int v, int L, int R) {
    int l = t[v].second.first; //Левая граница отрезка;
    int r = t[v].second.second; //Правая граница отрезка;
    if (r < L || l > R) {
        return std::make_pair(std::make_pair(INT_MIN, -1), std::make_pair(-1, -1));
    }
    if (r <= R && l >= L) {
        return t[v];
    }
    std::pair<std::pair<int, int>, std::pair<int ,int>> f1 = query(t, 2*v + 1, L, R);
    std::pair<std::pair<int, int>, std::pair<int ,int>> f2 = query(t, 2*v + 2, L, R);
    if (f1.first.first > f2.first.first) {
        return f1;
    } else if (f1.first.first < f2.first.first) {
        return f2;
    } else {
        return std::make_pair(std::make_pair(f1.first.first, f1.first.second + f2.first.second), f2.second);
    }
}

int main() {
	int n;
	std::cin >> n;
    int tmp = n;
    while ((n & (n - 1))!= 0) {
        n++;
    }
	std::vector<int> a(n);
	std::vector<std::pair<std::pair<int, int>, std::pair<int, int>>> t(2*n);

	for (int i = 0; i < n; i++) {
        if (i < tmp) {
            std::cin >> a[i];
        } else {
            a[i] = INT_MIN;
        }
	}
//    for (int pos: a) {
//        std::cout << pos << " ";
//    }
//    std::cout << "\n";
	build(a, t, 0, 0, n - 1);
//	for (std::pair<std::pair<int, int>, std::pair<int, int>> pos : t) {
//		std::cout << "(" << pos.first.first << "," << pos.first.second<< "), {" << pos.second.first << ";" << pos.second.second <<"}  -;-  ";
//	}
	int k;
	std::cin >> k;
    while (k > 0) {
        int l, r;
        std::cin >> l >> r;
//        l--;10
//3 8 6 4 2 5 9 0 7 1
//        r--;

        std::pair<std::pair<int, int>, std::pair<int, int>> res = query(t, 0, l, r);
//        std::cout << res.first.first << " " << res.first.second << " " << res.second.first  << " " << res.second.second <<"\n";
        std::cout << res.first.first << " " << res.first.second << "\n";
        k--;
    }

////
//    std::cout << res;
}