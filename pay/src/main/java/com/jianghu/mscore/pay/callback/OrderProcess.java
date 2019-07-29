package com.jianghu.mscore.pay.callback;

import com.jianghu.mscore.pay.vo.PrePayVo;

import java.util.Map;

@FunctionalInterface
public interface OrderProcess {

    void process(Map<String, String> map, PrePayVo vo);
}
