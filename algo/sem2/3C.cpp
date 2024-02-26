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

//int max(const std::vector<std::vector<int>>& sparseTableMax, const std::vector<int>& logs, int l, int r) {
//    int p = logs[r - l + 1];
//    return std::max(sparseTableMax[p][r], sparseTableMax[p][l - (1 << p)]);
//}
//
//int min(const std::vector<std::vector<int>>& sparseTableMin, const std::vector<int>& logs, int l, int r) {
//    int p = logs[r - l + 1];
//    return std::min(sparseTableMin[p][r], sparseTableMin[p][l - (1 << p)]);
//}

int main() {

    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);

    int n;
    std::cin >> n;
    std::vector<int> logs(n + 1);
    logs[1] = 0;
    for (int i = 2; i < n + 1; i++)
    {
        logs[i] = logs[i / 2] + 1;
    }
    std::vector<std::vector<int>> sparseTableMin(logs[n] + 1);
    std::vector<std::vector<int>> sparseTableMax(logs[n] + 1);
//    sparseTableMin[0] = std::vector<int>(n);
//    sparseTableMax[0] = std::vector<int>(n);
    std::vector<int> a(n);
    std::vector<int> b(n);
    for (int i = 0; i < n; i++)
    {
//        std::cin >> sparseTableMax[0][i];
        std::cin >> a[i];
    }
    for (int i = 0; i < n; i++)
    {
        std::cin >> b[i];
//        std::cin >> sparseTableMin[0][i];
    }

    for (int i = 0; i < logs[n] + 1; i++)
    {
        sparseTableMin[i] = std::vector<int> (n);
        sparseTableMax[i] = std::vector<int> (n);
        for (int j = 0; j + (1 << i) <= n; j++)
        {
            if (i != 0)
            {
                sparseTableMin[i][j] = std::min(sparseTableMin[i - 1][j], sparseTableMin[i - 1][j + (1 << (i - 1))]);
            } else {
                sparseTableMin[0][j] = b[j];
            }
        }
        for (int j = 0; j + (1 << i) <= n; j++)
        {
            if (i != 0)
            {
                sparseTableMax[i][j] = std::max(sparseTableMax[i - 1][j], sparseTableMax[i - 1][j + (1 << (i - 1))]);
            } else {
                sparseTableMax[0][j] = a[j];
            }
        }
    }

//    for (int i = 0; i < logs[n] + 1; i++)
//    {
//        for (int j = 0; j < n; j++)
//        {
//            std::cout << sparseTableMax[i][j] << " ";
//        }
//        std::cout << "\n";
//    }
//    std::cout << "\n";
//
//    for (int i = 0; i < logs[n] + 1; i++)
//    {
//        for (int j = 0; j < n; j++)
//        {
//            std::cout << sparseTableMin[i][j] << " ";
//        }
//        std::cout << "\n";
//    }
//    std::cout << "\n";
//    std::cout << "TRUE";
    int ansMax_l = 0;
    int ansMin_l = 0;
    int ansMax_r = 0;
    int ansMin_r = 0;
    long long count = 0;

    for (int i = 0; i < n; i++)
    {
        int l1 = i - 1;
        int l2 = i - 1;
        int r1 = n;
        int r2 = n;
        while (r1 - l1 > 1)
        {
            int mid = (l1 + r1)/2;
            int p_l = logs[mid - i + 1];
            ansMax_l = std::max(sparseTableMax[p_l][mid + 1 - (1 << p_l)], sparseTableMax[p_l][i]);
            ansMin_l = std::min(sparseTableMin[p_l][mid + 1 - (1 << p_l)], sparseTableMin[p_l][i]);
            if (ansMax_l <= ansMin_l) {
                l1 = mid;
            } else {
                r1 = mid;
            }
        }
        while (r2 - l2 > 1)
        {
            int mid = (l2 + r2)/2;
            int p_l = logs[mid - i + 1];
            ansMax_l = std::max(sparseTableMax[p_l][mid + 1 - (1 << p_l)], sparseTableMax[p_l][i]);
            ansMin_l = std::min(sparseTableMin[p_l][mid + 1 - (1 << p_l)], sparseTableMin[p_l][i]);
            if (ansMax_l < ansMin_l) {
                l2 = mid;
            } else {
                r2 = mid;
            }
        }
        count += l1 - l2;
    }
    std::cout << count;
    return 0;
}