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
import org.govhack.inclusiveteam.data.repository.StateRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The type Create states.
 */
public class CreateStates {

    private static StateRepository stateRepository;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static  void  main(String[] args)  {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});

        stateRepository = context.getBean(StateRepository.class);

        checkState("New South Wales", "NSW");
        checkState("Australian Capital Territory", "ACT");
        checkState("Northern Territory", "NT");
        checkState("Queensland", "QLD");
        checkState("Tasmania", "TAS");
        checkState("Western Australia", "WA");
        checkState("Victoria","VIC");
        checkState("South Australia", "SA");

        stateRepository.flush();

    }

    /**
     * Check state.
     *
     * @param name the name
     * @param abbr the abbr
     */
    protected static void checkState(String name, String abbr){
        State state = stateRepository.findByName(name);
        if (state == null) {
          state = new State(name, abbr);
          stateRepository.save(state);
        }
    }
}
