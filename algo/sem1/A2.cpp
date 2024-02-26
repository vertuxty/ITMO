#include<iostream>
#include<vector>
#include <cmath>

using namespace std;

int QuickSelect(vector<long long> &a, long long left, long long right, long long k) {
    long long i = left;
    long long j = right;
    long long mid = a[(left + right + 1) / 2];
    while (i <= j) {
        while (a[i] < mid) {
            ++i;
        }
        while (a[j] > mid) {
            --j;
        }
        if (i <= j) {
            swap(a[i], a[j]);
            --j;
            ++i;
        }
    }
    if (right - left <= 1) {
        return a[k];
    } else if (k < i) {
        return QuickSelect(a, left, j, k);
    } else {
        return QuickSelect(a, i, right, k);
    }
}

int main() {
    long long n;
    long long k;
    cin >> n;
    vector<long long> a(n);
    cin >> a[0];
    cin >> k;
    for (long long i = 1; i < n; ++i) {
        a[i] = (1103515245 * a[i - 1] + 12345) % (long long)pow(2, 31);
    }
    cout << QuickSelect(a, 0, n - 1, k) << endl;
}