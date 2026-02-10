package com.luoyx.hauyne.validation.group;


import com.luoyx.hauyne.validation.group.sequence.First;
import com.luoyx.hauyne.validation.group.sequence.Fourth;
import com.luoyx.hauyne.validation.group.sequence.Second;
import com.luoyx.hauyne.validation.group.sequence.Third;

import jakarta.validation.GroupSequence;

/**
 * 按校验顺序执行参数校验
 *
 * @author Alan.Luo
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class})
public interface GroupOrdered {

}