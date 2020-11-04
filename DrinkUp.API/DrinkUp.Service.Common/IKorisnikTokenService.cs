using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface IKorisnikTokenService
    {
        Task<IEnumerable<IKorisnikTokenModel>> GetAsync(GetParams<IKorisnikTokenModel> getParams);

        Task<IKorisnikTokenModel> GetAsync(int id);

        Task InsertAsync(IKorisnikTokenModel entity);

        Task UpdateAsync(IKorisnikTokenModel entity);

        Task DeleteAsync(int id);
    }
}
