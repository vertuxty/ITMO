#include <iostream>
#include <vector>
std::vector<std::vector<std::vector<int>>> t;
int sum(int x, int y, int z)
{
    int ans = 0;
    for (int i = x; i >= 0; i = (i & (i + 1)) - 1)
    {
        for (int j = y; j >= 0; j = (j & (j + 1)) - 1)
        {
            for (int k = z; k >= 0; k = (k & (k + 1)) - 1)
            {
                ans += t[i][j][k];
            }
        }
    }
    return ans;
}

void inc(int n, int x, int y, int z, int diff)
{
    int i = x;
    while (i < n)
    {
        int j = y;
        while (j < n)
        {
            int k = z;
            while (k < n)
            {
                t[i][j][k] += diff;
                k = (k | (k + 1));
            }
            j = (j | (j + 1));
        }
        i = (i | (i + 1));
    }
}

int main() {
    std::cin.tie(nullptr);
    std::ios_base::sync_with_stdio(false);
    int n;
    std::cin >> n;
    t.resize(n,
               std::vector<std::vector<int>>(n,
                       std::vector<int>(n, 0)));
    int m = 0;
    while (std::cin >> m)
    {
        if (m == 1)
        {
            int x, y, z, diff;
            std::cin >> x >> y >> z >> diff;
            inc(n, x, y, z, diff);
        } else if (m == 2) {
            int x1, y1, z1;
            int x2, y2, z2;
            std::cin >> x1 >> y1 >> z1 >> x2 >> y2 >> z2;
            int ans = sum(x2, y2, z2) - sum(x2, y2, z1 - 1) - sum(x2, y1 - 1, z2) + sum(x2, y1 - 1, z1 - 1) - sum(x1 - 1, y2, z2)
                    + sum(x1 - 1, y1 - 1, z2) + sum(x1 - 1, y2, z1 - 1) - sum(x1 - 1, y1 - 1, z1 - 1);
            std::cout << ans << "\n";
        } else {
            break;
        }
    }
    return 0;
}