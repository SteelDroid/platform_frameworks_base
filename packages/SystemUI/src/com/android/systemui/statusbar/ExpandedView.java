/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ExpandedView extends LinearLayout {
    StatusBarService mService;
    ItemTouchDispatcher mTouchDispatcher;
    int mPrevHeight = -1;

    public ExpandedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /** We want to shrink down to 0, and ignore the background. */
    @Override
    public int getSuggestedMinimumHeight() {
        return 0;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mTouchDispatcher.needsInterceptTouch(event)) {
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = mTouchDispatcher.handleTouchEvent(event);

        if (super.onTouchEvent(event)) {
            handled = true;
        }

        return handled;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int height = bottom - top;
        if (height != mPrevHeight) {
            mPrevHeight = height;
            mService.updateExpandedViewPos(StatusBarService.EXPANDED_LEAVE_ALONE);
        }
    }
}
