using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface IPonudaService
    {
        Task<IEnumerable<IPonudaModel>> GetAsync(GetParams<IPonudaModel> getParams);

        Task<IPonudaModel> GetAsync(int id);

        Task InsertAsync(IPonudaModel entity);

        Task UpdateAsync(IPonudaModel entity);

        Task DeleteAsync(int id);
    }
}
