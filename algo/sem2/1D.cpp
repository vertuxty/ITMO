#include <iostream>
#include <vector>
#include <math.h>
#include <iostream>
#include <vector>
#include <math.h>


struct Node {
	int value;
	int cnt;
	int sum;
	Node() = default;
	Node (int x, int count, int sums)
	{
		value = x;
		cnt = count;
		sum = sums;
	}
};


Node combine(Node v1, Node v2)
{
	Node newNode{};
	newNode.cnt = v1.cnt + v2.cnt;
	newNode.sum = v1.sum + v2.sum;
	return newNode;
	//	if (v1.value > v2.value)
	//	{
	//		return {v1.value};
	//	}
	//	return {v2.value};
}

void build(const int a[], Node t[], int v, int l, int r) {
	if (l == r) {
		if (a[l] != 0)
		{
			t[v] = Node(1, 0, 1);
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

int query_sum(Node t[], int v, int l, int r, int L, int R) {
	if (l > r) {
		return 0;
	}
	if (l == L && R == r)
	{
		return t[v].cnt;
	}
	int m = (L + R)/2;
	int one = query_sum(t, v * 2, l, std::min(r, m), L, m);
	int two = query_sum(t, v * 2 + 1, std::max(l, m + 1), r, m + 1, R);
	return one + two;
}

long long query(Node t[], int v, int l ,int r, int L, int R, int k)
{
	//	std::cout << "cnt: " << t[v].cnt << " v: " << v << "\n";
	if (l > r || t[v].cnt < k) {
		return INT_MIN;
	}
	if (L == R && R<= r && L >= l) {
		return R;
	} else if (L == R)
	{
		return INT_MIN;
	}
	int m = (L + R)/2;
	//	std::cout << m << "\n";
	int sum_left = l <= m ? query_sum(t, 2*v, l, std::min(r, m), L, m): 0;
	//	std::cout << "\n sum_left: " << sum_left << " k: " << k << "\n";
	if (sum_left >= k)
	{
		return query(t, v * 2, l, std::min(r, m), L, m, k);
	} else {
		return query(t, v * 2 + 1, std::max(l, m + 1), r, m + 1, R, k - sum_left);
	}
}

void update(Node t[], int v, int i, int x, int l, int r)
{
	if (l == r) {
		if (x != 0)
		{
			t[v] = Node(1, 0, 1);
		} else {
			t[v] = Node(0, 1, 0);
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
	//	std::cout << t[1].cnt << " mde\n";
	std::string query_;
	int m;
	std::cin >> m;
	while (m > 0) {
		std::cin >> query_;
		if (query_ == "s")
		{
			int l, r, k;
			std::cin >> l >> r >> k;
			int c = query(t, 1, l - 1, r - 1, 0, n - 1, k);
			if (c == INT_MIN)
			{
				std::cout << -1 << "\n";
			}
			else
			{
				std::cout << c + 1 << "\n";
			}
		} else {
			int i, x;
			std::cin >> i >> x;
			update(t, 1, i - 1, x, 0, n - 1);
		}
		m--;
	}
}