using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface IObjektPonudaService
    {
        Task<IEnumerable<IObjektPonudaModel>> GetAsync(GetParams<IObjektPonudaModel> getParams);

        Task<IObjektPonudaModel> GetAsync(int id);

        Task InsertAsync(IObjektPonudaModel entity);

        Task UpdateAsync(IObjektPonudaModel entity);

        Task DeleteAsync(int id);
    }
}
