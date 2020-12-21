using AutoMapper;
using DrinkUp.Common;
using DrinkUp.DAL.Entities;
using DrinkUp.Models.Common;
using DrinkUp.Repository;
using DrinkUp.Service.Common;
using QRCoder;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service
{
    public class TokenService : ITokenService
    {
        protected GenericRepository<Token> Repository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public TokenService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.TokenRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(string id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<ITokenModel>> GetAsync(GetParams<ITokenModel> getParams)
        {
            return Mapper.Map<ICollection<ITokenModel>>(await Repository.Get(Mapper.Map<GetParams<Token>>(getParams)));
        }

        public async Task<ITokenModel> GetAsync(string id)
        {
            return Mapper.Map<ITokenModel>(await Repository.GetByID(id));
        }

        public async Task<byte[]> InsertAsync(ITokenModel entity)
        {
            string id;
            using (SHA1 hash = SHA1.Create())
            {
                Guid guid = Guid.NewGuid();
                byte[] bytes = hash.ComputeHash(guid.ToByteArray());

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < bytes.Length; i++)
                {
                    builder.Append(bytes[i].ToString("x2"));
                }
                id = builder.ToString();
            }

            QRCodeGenerator generator = new QRCodeGenerator();
            QRCodeData codeData = generator.CreateQrCode("https://air2006.azurewebsites.net/api/token/activate?id=" + id, QRCodeGenerator.ECCLevel.Q);
            QRCode code = new QRCode(codeData);
            Bitmap image = code.GetGraphic(20);

            byte[] imageBytes;
            using (MemoryStream memoryStream = new MemoryStream())
            {
                image.Save(memoryStream, ImageFormat.Png);
                imageBytes = memoryStream.ToArray();
            }

            entity.Id = id;
            entity.QR = imageBytes;

            Repository.Insert(Mapper.Map<Token>(entity));
            await unitOfWork.SaveAsync();
            return imageBytes;
        }

        public async Task UpdateAsync(ITokenModel entity)
        {
            Repository.Update(Mapper.Map<Token>(entity));
            await unitOfWork.SaveAsync();
        }
    }
}
