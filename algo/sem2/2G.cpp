#include<iostream>
#include<cstdlib>
#include<cstdio>
#include<map>
#include<set>
#include<string>
#include<cstring>
#include<algorithm>
#include<queue>
#include<vector>
#include<math.h>

long long sum(const long long tree[], int x)
{
	long long ans = 0;
	for (int i = x; i > 0; i = (i & (i + 1)) - 1)
	{
		ans += tree[i];
	}
	return ans;
}

void inc(long long tree[], int n, int x, int diff)
{
	int i = x;
	while (i < n)
	{
		tree[i] += diff;
		i = (i | (i + 1));
	}
}

int main()
{
	int n;
	long long t;
	std::cin >> n >> t;
	static long long a[200005];
	static long long sum_pref[200005];
	sum_pref[0] = 0;
	static long long help[200005];
	static long long tree[200005];
	for (int i = 1; i < n + 1; i++)
	{
		std::cin >> a[i];
		sum_pref[i] = a[i] + sum_pref[i - 1];
		help[i] = sum_pref[i];
	}
	//std::cout << "OKAY!\n";
	std::sort(help, help + n + 1);
	//std::cout << "Okay2\n!";
	long long ans = 0;
	for (int i = 1; i < n + 1; i++)
	{
		int index_insertion = (int) (std::lower_bound(help, help + n + 1, sum_pref[i - 1]) - help + 1);
//		std::cout << index_insertion << " ind_ins\n";
		inc(tree, n + 2, index_insertion, 1);
		int other_index = (int) (std::upper_bound(help, help + n + 1, sum_pref[i] - t) - help);
//		std::cout << other_index << " upp_bound\n";
		long long query_result = sum(tree, other_index);
//		std::cout << query_result << " query_res\n";
		ans += i - query_result;
//		std::cout << ans << " curr_ans\n";
	}
	std::cout << ans;
}