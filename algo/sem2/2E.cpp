#include <iostream>
#include <vector>
#include <math.h>
#include <iostream>
#include <vector>
#include <math.h>
std::vector<long long> t;
long long sums(int x)
{
	long long ans = 0;
	for (int i = x; i >= 0; i = (i & (i + 1)) - 1)
	{
		ans += t[i];
	}
	return ans;
}

void inc(int n, int x)
{
	int i = x;
	while (i < n)
	{
		t[i] += 1;
		i = (i | (i + 1));
	}
}

int main() {
	int n;
	std::cin >> n;
	int mx_sz = 200100;
	std::vector<int> ans(n + 1);
	for (int i = 0; i < n + 1; i++)
	{
		ans[i] = 0;
	}
	t.resize(mx_sz);
	for (int i = 0; i < mx_sz; i++)
	{
		t[i] = 0;
	}
	int x, y;
	//Т.к. у нас y иду по возрастанию, что дерево фенвика подходит идеально, поскольку
	//При поиске суммы мы не будем идти дальше текущего Y, а при обновлении все норм, т.к будет изи тема!
	for (int i = 0; i < n; i++)
	{
		std::cin >> x >> y;
		long long count = sums(++x);
		ans[count] += 1;
		inc(mx_sz, x);
	}
	for (int i = 0; i < n; i++)
	{
		std::cout << ans[i] << "\n";
	}
}