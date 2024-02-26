#include <iostream>
#include <vector>
#include <math.h>
#include <iostream>
#include <vector>
#include <math.h>


struct Node {
	int value;
	Node() = default;
	Node (int x)
	{
		value = x;
	}
};


Node combine(Node v1, Node v2)
{
	if (v1.value > v2.value)
	{
		return {v1.value};
	}
	return {v2.value};
}

void build(const int a[], Node t[], int v, int l, int r) {
	if (l == r) {
		t[v] = Node(a[l]);
	}
	else {
		int m = (l + r) / 2;
		build(a, t, v * 2, l, m);
		build(a, t, 2 * v + 1, m + 1, r);
		t[v] = combine(t[2*v], t[2 * v + 1]);
	}
}

long long query(Node t[], int v, int l ,int r, int i, int x)
{
	if (r < i || t[v].value < x) {
		return INT_MIN;
	}
	if (l == r) {
		return l;
	}
	int m = (l + r)/2;
	long long left = query(t, v * 2, l, m, i, x);
	if (left != INT_MIN)
	{
		return left;
	}
	return query(t, v * 2 + 1, m + 1, r, i, x);
}

void update(Node t[], int v, int i, int x, int l, int r)
{
	if (l == r) {
		t[v] = Node(x);
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
	int m;
	std::cin >> m;
	for (int i = 0; i < n; i++) {
		std::cin >> a[i];
	}
	build(a, t, 1, 0, n - 1);
	int query_;
	while (m > 0) {
		std::cin >> query_;
		if (query_ == 1)
		{
			int i, x;
			std::cin >> i >> x;
			long long a = query(t, 1, 0, n - 1, i - 1, x);
			if (a == INT_MIN)
			{
				std::cout << -1 << "\n";
			}
			else
			{
				std::cout << a + 1 << "\n";
			}
		} else {
			int i, x;
			std::cin >> i >> x;
			update(t, 1, i - 1, x, 0, n - 1);
		}
		m--;
	}
}