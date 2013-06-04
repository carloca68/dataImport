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

import org.govhack.inclusiveteam.data.model.State;
import org.govhack.inclusiveteam.data.services.file.CSVReaderService;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImportValuesByYear extends AbstractImporter {

    private static final String DIR = "./data/new/";

    private static final String FILE_NAME = DIR + "byYear.csv";

    private static final String INFO = "Census by year";

    public static void main(String[] args) {

        initBeans();

        try {
            List<String[]> fileNamesList = readFileList(FILE_NAME);
            for (String[] file : fileNamesList) {
                State state = stateRepository.findByName(file[1]);
                if (state != null){
                    LineProcessor lineProcessor = new CensusLineProcessor(0);
                    CSVReaderService csvReaderService = new CSVReaderService(DIR + file[0], ',', '"', 0, 12, 34, lineProcessor);
                    csvReaderService.readFile();
                    System.out.println("Processing: " + file[0] + " Lines: " + lineProcessor.getSize());
                    processData(lineProcessor.getData(), state, file);
                }  else
                {
                  throw new IllegalArgumentException("State not found: " + file[1]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void processData(Map<String, String[]> data, State state, String[] file) {

        Set<String> keys = data.keySet();
        //System.out.println(state.getName());
        int nData = file.length;
        for (String key : keys) {
            String[] line = data.get(key);

            for (int index = 2; index < nData; index++){
                Long year = valueGetFromColumn(index, file);
                Long value = valueGetFromColumn(index - 1, line);
                String countryName = line[0];
                checkSource(year, INFO);
                checkCountry(countryName);
                processByYear(countryName,state, year, value);
                //System.out.println(" Year: " + year + " Country: " + line[0] + " Total: " + value);
                //System.out.println("");
            }

        }
    }
}
