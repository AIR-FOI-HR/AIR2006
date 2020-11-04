using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface IVrstaPonudeService
    {
        Task<IEnumerable<IVrstaPonudeModel>> GetAsync(GetParams<IVrstaPonudeModel> getParams);

        Task<IVrstaPonudeModel> GetAsync(int id);

        Task InsertAsync(IVrstaPonudeModel entity);

        Task UpdateAsync(IVrstaPonudeModel entity);

        Task DeleteAsync(int id);
    }
}
