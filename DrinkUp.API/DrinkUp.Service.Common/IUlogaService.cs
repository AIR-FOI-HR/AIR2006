using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface IUlogaService
    {
        Task<IEnumerable<IUlogaModel>> GetAsync(GetParams<IUlogaModel> getParams);

        Task<IUlogaModel> GetAsync(int id);

        Task InsertAsync(IUlogaModel entity);

        Task UpdateAsync(IUlogaModel entity);

        Task DeleteAsync(int id);
    }
}
