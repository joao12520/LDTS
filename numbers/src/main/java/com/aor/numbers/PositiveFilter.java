package com.aor.numbers;

import com.aor.numbers.IListFilter;

public class PositiveFilter implements IListFilter {
    public PositiveFilter(){}

    public boolean accept(Integer number){
        return (number > 0);
    }
}