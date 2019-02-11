#include<vector>
#include<string>
#include<iostream>

void help_p(int num_left, int num_right, const std::string& valid_prefix,
            std::vector<std::string>* result){
    if(!num_right){
        result->emplace_back(valid_prefix);
        return;
    }

    if (num_left > 0){
        help_p(num_left - 1, num_right, valid_prefix + "(", result);
    }

    if (num_left < num_right){
        help_p(num_left, num_right-1, valid_prefix + ")", result);
    }
}

std::vector<std::string> gen_p(int num_pairs){
    std::vector<std::string> result;
    help_p(num_pairs,num_pairs, "", &result);
    return result;
}




int main() {
    auto P = gen_p(15);
    for (int i = 0; i < P.size(); ++i){
        std::cout << P[i] << std::endl;
    }
}
