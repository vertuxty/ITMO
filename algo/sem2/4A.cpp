#include <iostream>
#include <vector>
#include <math.h>
#include "string"
#include "array"
#include "list"
#include "unordered_set"
int main() {
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);
    long long m = 14177;
    long long p = 103967;
    long long a = 3491;
    long long b = 14240057;
    std::array<std::list<int>, 14177> set;
//    std::cout << (-10) %p;
    std::string query;
    long long value;
    while (std::cin >> query >> value)
    {
        int ind = (((a*abs(value) + b) % p) % m);
        if (query == "insert")
        {
            if (set[ind].empty())
            {
                set[ind].push_back(value);
            } else {
                int fl = 0;
                for (int i : set[ind])
                {
                    if (i == value){
                        fl = 1;
                        break;
                    }
                }
                if (fl != 1)
                {
                    set[ind].push_back(value);
                }
            }
        } else if (query == "exists")
        {
            if (set[ind].empty())
            {
                std::cout << "false\n";
            } else {
                int fl = 0;
                for (int i : set[ind])
                {
                    if (i == value){
                        fl = 1;
                        std::cout << "true\n";
                        break;
                    }
                }
                if (fl == 0)
                {
                    std::cout << "false\n";
                }
            }
        } else {
            if (!set[ind].empty())
            {
                set[ind].remove(value);
            }
        }
    }


    return 0;
}