/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.comparator;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import java.util.Comparator;

/**
 *
 * @author janaka_h
 */
public class CMSSectionComparator implements Comparator<SectionBean> {

    public int compare(SectionBean _first, SectionBean _second) {
        return _first.getSortKey().compareTo(_second.getSortKey());
    }
}
