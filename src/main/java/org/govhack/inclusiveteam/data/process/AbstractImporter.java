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

import au.com.bytecode.opencsv.CSVReader;
import org.govhack.inclusiveteam.data.model.Country;
import org.govhack.inclusiveteam.data.model.DemographicInfo;
import org.govhack.inclusiveteam.data.model.Source;
import org.govhack.inclusiveteam.data.model.State;
import org.govhack.inclusiveteam.data.repository.CountryRepository;
import org.govhack.inclusiveteam.data.repository.DemographicInfoRepository;
import org.govhack.inclusiveteam.data.repository.SourceRepository;
import org.govhack.inclusiveteam.data.repository.StateRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Abstract exporter.
 */
public abstract class AbstractImporter {

    /**
     * The constant STATE_NAMES.
     */
    protected static final String[] STATE_NAMES = {
            "Australian Capital Territory",
            "New South Wales",
            "Queensland",
            "South Australia",
            "Tasmania",
            "Western Australia",
            "Northern Territory",
            "Victoria"};

    /**
     * The constant stateRepository.
     */
    protected static StateRepository stateRepository;
    /**
     * The constant sourceRepository.
     */
    protected static SourceRepository sourceRepository;
    /**
     * The constant countryRepository.
     */
    protected static CountryRepository countryRepository;
    /**
     * The constant infoRepository.
     */
    protected static DemographicInfoRepository infoRepository;

    /**
     * The constant run.
     */
    protected static boolean run = false;

    /**
     * Init beans.
     */
    protected static void initBeans() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});

        stateRepository = context.getBean(StateRepository.class);
        sourceRepository = context.getBean(SourceRepository.class);
        countryRepository = context.getBean(CountryRepository.class);
        infoRepository = context.getBean(DemographicInfoRepository.class);
    }

    /**
     * Value get from column.
     *
     * @param index the index
     * @param input the input
     * @return the long
     */
    protected static Long valueGetFromColumn(int index, String[] input) {
        try {
            String valueString = input[index].replace(",", "");
            return Long.parseLong(valueString);
        } catch (Exception ex) {
            return 0L;
        }

    }

    /**
     * Check source.
     *
     * @param year the year
     * @param info the info
     */
    protected static void checkSource(Long year, String info) {
        Source source = sourceRepository.findByYear(year);
        if (source == null) {
            source = new Source(year, info + " - " + year);
            sourceRepository.save(source);
        }
    }

    /**
     * Process by year.
     *
     * @param countryName the country name
     * @param state       the state
     * @param year        the year
     * @param value       the value
     */
    protected static void processByYear(String countryName, State state, Long year, Long value) {

        Source source = sourceRepository.findByYear(year);
        Country country = countryRepository.findByName(countryName);
        DemographicInfo info = new DemographicInfo(country, state, source, value);
        infoRepository.save(info);
    }

    /**
     * Read file list.
     *
     * @param fileName the file name
     * @return the list
     * @throws FileNotFoundException the file not found exception
     */
    protected static List<String[]> readFileList(String fileName) throws FileNotFoundException {
        ArrayList<String[]> output = new ArrayList<String[]>();
        CSVReader reader = new CSVReader(new FileReader(fileName), ';', '"');
        String[] nextLine;
        try {
            while ((nextLine = reader.readNext()) != null) {
                output.add(nextLine);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return output;
    }

    /**
     * Check country.
     *
     * @param countryName the country name
     */
    protected static void checkCountry(String countryName) {
        Country country = countryRepository.findByName(countryName);
        if (country == null) {
            country = new Country(countryName);
            countryRepository.save(country);
        }
    }
}
