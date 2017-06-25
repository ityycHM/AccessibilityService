package com.example.yuanyongchao.accessibilityservice.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by yuanyongchao on 2017/6/25.
 */

public class WindowAccessibilitySeivice extends AccessibilityService{
    final String TAG = "WindowAccessibilitySeivice";
    String[] installPackge = {"com.android.packageinstaller"};
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        //监听过滤的包名
        accessibilityServiceInfo.packageNames = installPackge;
        //监听那些行为
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        //反馈
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        //通知时间
        accessibilityServiceInfo.notificationTimeout = 100;
        setServiceInfo(accessibilityServiceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(event.getSource()!=null){
            findAndPerformAction("安装");
            findAndPerformAction("下一步");
            findAndPerformAction("完成");
        }

    }

    @Override
    public void onInterrupt() {

    }
    private void findAndPerformAction(String text){
        // 查找当前窗口中包含“安装”文字的按钮
        if (getRootInActiveWindow() == null) {
            return;
        }
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes  = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node  = nodes.get(i);
            if (node.getClassName().equals("android.widget.Button")&&node.isEnabled()){
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }

    }

}
