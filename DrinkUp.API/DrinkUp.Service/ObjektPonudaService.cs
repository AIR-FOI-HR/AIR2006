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
    public class ObjektPonudaService : IObjektPonudaService
    {
        protected GenericRepository<ObjektPonuda> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public ObjektPonudaService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.ObjektPonudaRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<IObjektPonudaModel>> GetAsync(GetParams<IObjektPonudaModel> getParams)
        {
            return Mapper.Map<ICollection<IObjektPonudaModel>>(await Repository.Get(Mapper.Map<GetParams<ObjektPonuda>>(getParams)));
        }

        public async Task<IObjektPonudaModel> GetAsync(int id)
        {
            return Mapper.Map<IObjektPonudaModel>(await Repository.GetByID(id));
        }

        public async Task InsertAsync(IObjektPonudaModel entity)
        {
            Repository.Insert(Mapper.Map<ObjektPonuda>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task UpdateAsync(IObjektPonudaModel entity)
        {
            Repository.Update(Mapper.Map<ObjektPonuda>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}
