//import { render } from '@testing-library/react';
import React, { Component } from 'react';
import './Riesgos.css';
import { Button, Stack, Row, FormSelect, OverlayTrigger, Tooltip } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AddRiskModal from './Components/AddRiskModal';
import axios from 'axios';
import RisksTable from './Components/RisksTable';
import Search from './Components/Search';
import Pages from '../../SharedComponents/Pagination/Pages';
import Cookies from 'universal-cookie';
const cookies = new Cookies();

class Riesgos extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            sortingValue: 'id',
            sortingWay: 'desc',
            riesgos: [],
            typesMap: null,
            riesgosView: [],
            currentPage: 1,
            pageItemAmount: 10
        };
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updateRiesgos = this.updateRiesgos.bind(this);
        this.updateRiesgosBySearch = this.updateRiesgosBySearch.bind(this);
        this.updateRiesgosSort = this.updateRiesgosSort.bind(this);
        this.handleSortSelect = this.handleSortSelect.bind(this);
        this.handleSortClick = this.handleSortClick.bind(this);
        this.handleRiskRender = this.handleRiskRender.bind(this);
        this.updatePage = this.updatePage.bind(this);
        this.updatePageItems = this.updatePageItems.bind(this);
        this.retrieveTypes = this.retrieveTypes.bind(this);
    }
    //On load
    componentDidMount() {
        //Account check
        if (typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('full_name', { path: process.env.REACT_APP_AUTH }) === 'undefined') {
            document.location = process.env.REACT_APP_SIMSP_LOGOUT;
        }

        this.updateRiesgosSort();
    }

    updateRiesgosSort() {
        let sortingValue = this.state.sortingValue;
        let sortingWay = this.state.sortingWay;
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + "/RiskServlet/Retrieve/Riesgos",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            data: {
                'sortingValue': sortingValue,
                'sortingWay': sortingWay
            }
        }
        axios(options)
            .then(response => {
                this.setState({
                    riesgos: response.data,
                    currentPage: 1
                }, () => {
                    this.handleRiskRender();
                    this.retrieveTypes();
                });
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema recuperando los Riesgos.";
                            break;
                        case 500:
                            msj = "El servidor ha encontrado un error desconocido.";
                            break;
                        default:
                            msj = "El servidor ha encontrado un error desconocido.";
                            break;
                    }
                } else if (error.request) {
                    //Server did not respond
                    msj = "Hubo un error con la conexión al servidor."
                } else {
                    //Something else went wrong
                    msj = "Error desconocido."
                }
                toast.error(msj, {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            })
    };

    retrieveTypes() {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + `/RiskServlet/Retrieve/RiskType`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
        axios(options)
            .then(response => {
                let map = new Map();
                for (const [key, value] of Object.entries(response.data)) {
                    map.set(key, value);
                }
                this.setState({
                    typesMap: map
                });
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema recuperando los Tipos de Riesgos.";
                            break;
                        case 500:
                            msj = "El servidor ha encontrado un error desconocido.";
                            break;
                        default:
                            msj = "El servidor ha encontrado un error desconocido.";
                            break;
                    }
                } else if (error.request) {
                    //Server did not respond
                    msj = "Hubo un error con la conexión al servidor."
                } else {
                    //Something else went wrong
                    msj = "Error desconocido."
                }
                toast.error(msj, {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            })
    }

    /* Pagination */
    updatePage(pageNumber) {
        this.setState({
            currentPage: pageNumber
        }, () => {
            this.handleRiskRender();
        });
    }

    updatePageItems(amount) {
        this.setState({
            pageItemAmount: amount,
            currentPage: 1
        }, () => {
            this.handleRiskRender();
        })
    }

    handleRiskRender() {
        let items = [];
        let itemAmount = this.state.pageItemAmount;
        let pos = (this.state.currentPage - 1) * itemAmount;
        for (let i = 0; i < itemAmount; i++) {
            let item = this.state.riesgos.at(pos);
            if (typeof item !== 'undefined' && item !== null) {
                items.push(item);
            }
            pos++;
        }
        this.setState({ riesgosView: items });
    }

    updateRiesgos(type) {
        if (type === "add-success") {
            toast.success("El Riesgo ha sido agregado satisfactoriamente! Redireccionando a la página del Riesgo...", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 5000
            });
        }
        this.updateRiesgosSort();
    };

    /* Search */
    updateRiesgosBySearch(type) {
        this.setState({
            riesgos: type,
            currentPage: 1
        }, () => {
            this.handleRiskRender();
        });
    }

    /* Modal */
    openModal = () => {
        this.setState({ show: true });
    };

    closeModal = () => {
        this.setState({ show: false });
    };


    handleSortSelect = e => {
        this.setState(
            {
                sortingValue: e.target.value
            }, () => {
                this.updateRiesgosSort();
            }
        );
    }

    handleSortClick() {
        let sort = this.state.sortingWay;
        if (sort === 'desc') {
            sort = 'asc';
        } else {
            sort = 'desc';
        }
        this.setState(
            {
                sortingWay: sort
            }, () => {
                this.updateRiesgosSort();
            }
        );
    }

    render() {
        return (
            <div className="Riesgos-Container container-fluid">

                {/* Mobile */}

                <h1 className='mt-2 d-lg-none'>Gestor de Riesgos</h1>
                <Row className='mt-2 d-lg-none'>
                    <Search updateRiesgos={this.updateRiesgosBySearch} />
                </Row>

                <Row className="d-lg-none">
                    <Stack direction="horizontal">
                        {/* Agregar Riesgo */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Agregar Riesgo
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button className="btn-sfr" size="lg" onClick={this.openModal}>
                                <i className="bi bi-plus-circle"></i>
                            </Button>
                        </OverlayTrigger>
                        <FormSelect className='w-50' onChange={this.handleSortSelect}>
                            <option disabled>Ordenar por...</option>
                            <option value='pk_id' defaultValue>ID</option>
                            <option value='name'>Nombre</option>
                            <option value='generalType'>Tipo General</option>
                            <option value='areaType'>Tipo por Área</option>
                            <option value='probability'>Probabilidad</option>
                            <option value='impact'>Impacto</option>
                            <option value='magnitude'>Magnitud</option>
                        </FormSelect>
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    {(this.state.sortingWay === 'desc' ? "Descendente" : "Ascendente")}
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button id="sortButton" variant={(this.state.sortingWay === 'desc' ? "primary" : "danger")} onClick={this.handleSortClick}>
                                <i className="bi bi-arrow-down-up"></i>
                            </Button>
                        </OverlayTrigger>
                    </Stack>
                </Row>

                {/* PC */}
                <Row className="mt-2 d-none d-lg-block">
                    <Stack direction="horizontal" gap={3}>
                        <h5 className='mt-2'>Gestor de Riesgos</h5>
                        {/* Agregar Riesgo */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Agregar Riesgo
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button className="btn-sfr" size="lg" onClick={this.openModal}>
                                <i className="bi bi-plus-circle"></i>
                            </Button>
                        </OverlayTrigger>

                        <FormSelect className='w-50' onChange={this.handleSortSelect}>
                            <option selected disabled>Ordenar por...</option>
                            <option value='pk_id' defaultValue>ID</option>
                            <option value='name'>Nombre</option>
                            <option value='generalType'>Tipo General</option>
                            <option value='areaType'>Tipo por Área</option>
                            <option value='probability'>Probabilidad</option>
                            <option value='impact'>Impacto</option>
                            <option value='magnitude'>Magnitud</option>
                        </FormSelect>
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    {(this.state.sortingWay === 'desc' ? "Descendente" : "Ascendente")}
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button id="sortButton" variant={(this.state.sortingWay === 'desc' ? "primary" : "danger")} onClick={this.handleSortClick}>
                                <i className="bi bi-arrow-down-up"></i>
                            </Button>
                        </OverlayTrigger>

                        <Search updateRiesgos={this.updateRiesgosBySearch} />
                    </Stack>
                </Row>
                <Row>
                    <RisksTable
                        riesgos={this.state.riesgosView}
                        updateRiesgosSort={this.updateRiesgosSort}
                    />
                </Row>
                <Row>
                    <Pages
                        listLength={this.state.riesgos.length}
                        itemAmount={this.state.pageItemAmount}
                        updatePage={this.updatePage}
                        currentPage={this.state.currentPage}
                        updatePageItems={this.updatePageItems} />
                </Row>
                <AddRiskModal
                    show={this.state.show}
                    updateRiesgos={this.updateRiesgos}
                    closeModal={this.closeModal}
                    typesMap={this.state.typesMap} />
                <ToastContainer />
            </div>
        );
    }
};
export default Riesgos;