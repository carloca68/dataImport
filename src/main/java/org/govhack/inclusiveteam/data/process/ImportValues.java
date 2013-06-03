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

import org.govhack.inclusiveteam.data.model.Country;
import org.govhack.inclusiveteam.data.model.State;
import org.govhack.inclusiveteam.data.services.CSVReaderService;

import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Set;

/**
 * The type Import values.
 */
public class ImportValues extends AbstractImporter {

    private static final String[] FILE_NAMES = {
            "./data/input/20680-b10-Australian Capital Territory (State).csv",
            "./data/input/20680-b10-New South Wales (State).csv",
            "./data/input/20680-b10-Queensland (State).csv",
            "./data/input/20680-b10-South Australia (State).csv",
            "./data/input/20680-b10-Tasmania (State).csv",
            "./data/input/20680-b10-Western Australia (State).csv",
            "./data/input/20680-b10-Northern Territory (State).csv",
            "./data/input/20680-b10-Victoria (State).csv"};

    private static final int Y2000_INDEX = 3;
    private static final int Y2001_INDEX = 4;
    private static final int Y2002_INDEX = 5;
    private static final int Y2003_INDEX = 6;
    private static final int Y2004_INDEX = 7;
    private static final int Y2005_INDEX = 8;
    private static final int Y2006_INDEX = 9;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        if (!run) {
            return;
        }
        initBeans();

        for (int stateIndex = 0; stateIndex < STATE_NAMES.length; stateIndex++) {
            State state = stateRepository.findByName(STATE_NAMES[stateIndex]);

            if (state == null) {
                throw new InvalidParameterException("State not found!");
            }

            System.out.println(state);

            LineProcessor lineProcessor = new CensusLineProcessor(0);
            CSVReaderService csvReaderService = new CSVReaderService(FILE_NAMES[stateIndex], ',', '"', 0, 17, 34, lineProcessor);
            try {
                csvReaderService.readFile();

                processData(lineProcessor.getData(), state);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void processData(Map<String, String[]> data, State state) {

        Set<String> keys = data.keySet();
        for (String key : keys) {

            checkSource(2000L, "Census by year");
            checkSource(2001L, "Census by year");
            checkSource(2002L, "Census by year");
            checkSource(2003L, "Census by year");
            checkSource(2004L, "Census by year");
            checkSource(2005L, "Census by year");
            checkSource(2006L, "Census by year");

            Country country = countryRepository.findByName(key);
            if (country == null) {
                country = new Country(key);
                countryRepository.save(country);
            }

            processByYear(key, state, 2000L, valueGetFromColumn(Y2000_INDEX, data.get(key)));
            processByYear(key, state, 2001L, valueGetFromColumn(Y2001_INDEX, data.get(key)));
            processByYear(key, state, 2002L, valueGetFromColumn(Y2002_INDEX, data.get(key)));
            processByYear(key, state, 2003L, valueGetFromColumn(Y2003_INDEX, data.get(key)));
            processByYear(key, state, 2004L, valueGetFromColumn(Y2004_INDEX, data.get(key)));
            processByYear(key, state, 2005L, valueGetFromColumn(Y2005_INDEX, data.get(key)));
            processByYear(key, state, 2006L, valueGetFromColumn(Y2006_INDEX, data.get(key)));

            System.out.println(key + " - " + data.get(key));
        }
    }

}
