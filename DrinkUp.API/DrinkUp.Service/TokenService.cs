using AutoMapper;
using DrinkUp.Common;
using DrinkUp.DAL.Entities;
using DrinkUp.Models.Common;
using DrinkUp.Repository;
using DrinkUp.Service.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service
{
    public class TokenService : ITokenService
    {
        protected GenericRepository<TokenEntity> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public TokenService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.TokenRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<ITokenModel>> GetAsync(GetParams<ITokenModel> getParams)
        {
            return Mapper.Map<ICollection<ITokenModel>>(await Repository.Get(Mapper.Map<GetParams<TokenEntity>>(getParams)));
        }

        public async Task<ITokenModel> GetAsync(int id)
        {
            return Mapper.Map<ITokenModel>(await Repository.GetByID(id));
        }

        public async Task InsertAsync(ITokenModel entity)
        {
            Repository.Insert(Mapper.Map<TokenEntity>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task UpdateAsync(ITokenModel entity)
        {
            Repository.Update(Mapper.Map<TokenEntity>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}
