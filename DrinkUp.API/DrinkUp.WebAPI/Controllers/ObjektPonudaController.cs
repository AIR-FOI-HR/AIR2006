using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using AutoMapper;
using DrinkUp.Common;
using DrinkUp.Common.Filter;
using DrinkUp.Common.Page;
using DrinkUp.Common.Sort;
using DrinkUp.Models;
using DrinkUp.Models.Common;
using DrinkUp.Service.Common;
using DrinkUp.WebAPI.REST;
using DrinkUp.WebAPI.ViewModels;
using Microsoft.AspNetCore.Mvc;

namespace DrinkUp.WebAPI.Controllers
{
    [Route("api/objekt-ponuda")]
    [ApiController]
    public class ObjektPonudaController : ControllerBase
    {
        public IObjektPonudaService Service { get; }
        public IMapper Mapper { get; }
        public IFilter Filter { get; }
        public ISort Sort { get; }
        public IPagedResult PagedResult { get; }

        public ObjektPonudaController(IObjektPonudaService service, IMapper mapper, IFilter filter, ISort sort, IPagedResult pagedResult)
        {
            Service = service;
            Mapper = mapper;
            Filter = filter;
            Sort = sort;
            PagedResult = pagedResult;
        }

        [HttpGet]
        public async Task<ICollection<ObjektPonudaREST>> GetAsync(string filterColumn = "",
            string filterValue = "", int filterOption = 3, string sortBy = "",
            int sortOrder = 1, int pageSize = 10, int page = 1)
        {
            GetParams<ObjektPonudaModel> getParams = new GetParams<ObjektPonudaModel>()
            {
                PageNumber = page,
                PageSize = pageSize
            };
            FilterParams filterParams = new FilterParams()
            {
                ColumnName = filterColumn,
                FilterValue = filterValue,
                FilterOption = (FilterOptions)filterOption
            };
            SortingParams sortingParams = new SortingParams()
            {
                ColumnName = sortBy,
                SortOrder = (SortOrders)sortOrder
            };

            getParams.FilterParam = new[] { filterParams };
            getParams.SortingParam = new[] { sortingParams };
            getParams.Filter = Filter;
            getParams.Sort = Sort;
            getParams.Page = PagedResult;

            return Mapper.Map<List<ObjektPonudaREST>>(await Service.GetAsync(Mapper.Map<GetParams<IObjektPonudaModel>>(getParams)));
        }

        [HttpGet("{id}")]
        public async Task<ObjektPonudaREST> GetAsync(int id)
        {
            return Mapper.Map<ObjektPonudaREST>(await Service.GetAsync(id));
        }

        [HttpPost("add")]
        public async Task<HttpResponseMessage> AddAsync(ObjektPonudaVM uloga)
        {
            try
            {
                await Service.InsertAsync(Mapper.Map<ObjektPonudaModel>(uloga));
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.Created);
        }

        [HttpPut("update")]
        public async Task<HttpResponseMessage> UpdateAsync(ObjektPonudaVM uloga)
        {
            try
            {
                await Service.UpdateAsync(Mapper.Map<ObjektPonudaModel>(uloga));
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }

        [HttpDelete("delete/{id}")]
        public async Task<HttpResponseMessage> DeleteAsync(int id)
        {
            try
            {
                await Service.DeleteAsync(id);
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }
    }
}
