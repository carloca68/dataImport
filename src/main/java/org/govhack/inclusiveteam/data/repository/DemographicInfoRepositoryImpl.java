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

package org.govhack.inclusiveteam.data.repository;

import org.govhack.inclusiveteam.data.model.Statistics;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * The type Demographic info repository custom impl.
 */
public class DemographicInfoRepositoryImpl implements DemographicInfoRepositoryCustom {

    @PersistenceContext
    private javax.persistence.EntityManager em;

    @Override
    public List<Statistics> calculateByYear(Long year, int maxSize) {
        Query query = em.createQuery(" select new org.govhack.inclusiveteam.data.model.Statistics(country.name, sum(info.value))" +
                " from INFO_OLAP info, COUNTRY_OLAP country, SOURCE_OLAP source where " +
                "        info.country = country " +
                "        and info.source = source " +
                "        and country.id <> 30 " +
                "        and info.source.year = ?1 " +
                "        group by country.name, source.year " +
                "        order by sum(info.value) desc");
        query.setParameter(1, year);
        return query.setMaxResults(maxSize).getResultList();
    }
}
