using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface ITokenService
    {
        Task<IEnumerable<ITokenModel>> GetAsync(GetParams<ITokenModel> getParams);

        Task<ITokenModel> GetAsync(string id);

        Task<byte[]> InsertAsync(ITokenModel entity);

        Task UpdateAsync(ITokenModel entity);

        Task DeleteAsync(int id);
    }
}
