/*
 *
 *  Created by Carlos - carloca68@gmail.com
 *
 *  Copyright (c) 2013 GovHack 2013 - InclusiveTeam
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the project's author nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 *  TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * /
 */

package org.govhack.inclusiveteam.data.process;

import org.govhack.inclusiveteam.data.model.DemographicInfo;

import java.util.Comparator;

/**
 * The type Demographic info value comparator.
 */
public class DemographicInfoValueComparator implements Comparator<DemographicInfo> {

    private boolean reverse = false;

    public DemographicInfoValueComparator(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public int compare(DemographicInfo info1, DemographicInfo info2) {
        Long cmp = info1.getValue() - info2.getValue();
        int result = 0;
        if (cmp > 0L) {
            result = 1;
        }
        if (cmp < 0L) {
            result = -1;
        }
        if (reverse) {
            return  result * -1;
        }
        return result;
    }

    public static DemographicInfoValueComparator newInstance(boolean reverse){
        return new DemographicInfoValueComparator(reverse);
    }

    public static DemographicInfoValueComparator newInstance(){
        return new DemographicInfoValueComparator(false);
    }
}
