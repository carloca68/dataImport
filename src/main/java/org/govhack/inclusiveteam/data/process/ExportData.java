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

import com.bbn.openmap.geo.GeoPoint;
import org.govhack.inclusiveteam.data.model.DemographicInfo;
import org.govhack.inclusiveteam.data.repository.CountryRepository;
import org.govhack.inclusiveteam.data.repository.DemographicInfoRepository;
import org.govhack.inclusiveteam.data.repository.SourceRepository;
import org.govhack.inclusiveteam.data.repository.StateRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportData
{

    private static StateRepository stateRepository;
    private static SourceRepository sourceRepository;
    private static CountryRepository countryRepository;
    private static DemographicInfoRepository infoRepository;


    private static boolean run = true;

    private GeoPoint sydney = new GeoPoint.Impl(151.0281982,-33.88977432);
    private GeoPoint adelaide = new GeoPoint.Impl(138.8706818,-34.91853714);
    private GeoPoint canberra = new GeoPoint.Impl(149.041626,-35.34992599);
    private GeoPoint melbourne = new GeoPoint.Impl(145.0751038,-37.85295868);
    private GeoPoint perth = new GeoPoint.Impl(115.9233704,-31.97586441);
    private GeoPoint hobart = new GeoPoint.Impl(147.5,-43);
    private GeoPoint brisbane = new GeoPoint.Impl(153.0264893,-27.45391273);
    private GeoPoint darwin = new GeoPoint.Impl(130.9945526,-12.70149994);


    public static void main(String[] args) {

        if (!run){
            return;
        }

        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});

        stateRepository = context.getBean(StateRepository.class);
        sourceRepository = context.getBean(SourceRepository.class);
        countryRepository = context.getBean(CountryRepository.class);
        infoRepository = context.getBean(DemographicInfoRepository.class);

        List<DemographicInfo> info = infoRepository.findBySource_YearAndState_Name(2001L, "Queensland");

        Collections.sort(info, DemographicInfoValueComparator.newInstance(true));

        processData(10, info);


    }

    private static void processData(int size, List<DemographicInfo> infoList) {
        Map<String, Long> mappedData = new HashMap<String, Long>();
        for (int i = 0; i < size; i++){
            DemographicInfo info = infoList.get(i);
            List<GeoPoint> points = createPoints(info);
           // System.out.println(info.getCountry().getName() + " - " + info.getValue());
        }


    }

    private static List<GeoPoint> createPoints(DemographicInfo info) {

        return null;
    }

}
