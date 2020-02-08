/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.comparator;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import java.util.Comparator;

/**
 *
 * @author janaka_h
 */
public class CMSPageComparator implements Comparator<PageBean>{
    
     public int compare(PageBean _first, PageBean _second) {
            return _first.getSortKey().compareTo(_second.getSortKey());
    }
    
}
