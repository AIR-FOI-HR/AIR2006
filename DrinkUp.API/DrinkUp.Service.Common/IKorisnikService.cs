﻿using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface IKorisnikService
    {
        Task<IEnumerable<IKorisnikModel>> GetAsync(GetParams<IKorisnikModel> getParams);

        Task<IKorisnikModel> GetAsync(int id);

        Task InsertAsync(IKorisnikModel entity);

        Task UpdateAsync(IKorisnikModel entity);

        Task DeleteAsync(int id);
    }
}
