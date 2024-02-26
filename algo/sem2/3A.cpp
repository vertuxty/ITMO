#include <iostream>
#include <vector>
#include "string"
#include "array"
#include "list"
#include "cmath"
#include "unordered_set"

//struct Node {
//    std::string key;
//    std::string value;
//    int hashCode = -1;
//    Node* next;
//    Node* prev;
//};
int main() {
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);

    int n, m, a1, u1, v1;
    std::cin >> n >> m >> a1 >> u1 >> v1;

    std::vector<int> a(n);
    a[0] = a1;
    for (int i = 1; i < n; i++)
    {
        a[i] = (23 * a[i - 1] + 21563) % 16714589;
    }

    std::vector<int> logs(n + 1);
    logs[1] = 0;
    for (int i = 2; i < n + 1; i++)
    {
        logs[i] = logs[i / 2] + 1;
    }

    std::vector<std::vector<int>> sparseTable(logs[n] + 1);
    for (int i = 0; i < logs[n] + 1; i++)
    {
        sparseTable[i] = std::vector<int> (n);
        for (int j = 0; j + (1 << i) <= n; j++)
        {
            if (i != 0)
            {
                sparseTable[i][j] = std::min(sparseTable[i - 1][j], sparseTable[i - 1][j + (1 << (i - 1))]);
            } else {
                sparseTable[0][j] = a[j];
            }
        }
    }
    int ans = 0;
    if (u1 > v1) {
        int p = logs[u1 - v1 + 1];
        ans = std::min(sparseTable[p][v1 - 1], sparseTable[p][u1 - (1 << p)]);
    } else {
        int p = logs[v1 - u1 + 1];
        ans = std::min(sparseTable[p][u1 - 1], sparseTable[p][v1 - (1 << p)]);
    }
    int prev_u = u1;
    int prev_v = v1;
    for (int i = 1; i < m; i++) {
        u1 = ((17 * prev_u + 751 + ans + 2 * i) % n) + 1;
        v1 = ((13 * prev_v + 593 + ans + 5 * i) % n) + 1;
        if (v1 > u1) {
            int p = logs[v1 - u1 + 1];
            ans = std::min(sparseTable[p][u1 - 1], sparseTable[p][v1 - (1 << p)]);
        } else {
            int p = logs[u1 - v1 + 1];
            ans = std::min(sparseTable[p][v1 - 1], sparseTable[p][u1 - (1 << p)]);
        }
        prev_u = u1;
        prev_v = v1;
    }
    std::cout << u1 << " " << v1 << " " << ans;

    return 0;
}