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
import GenericModal from '../../SharedComponents/GenericModal/GenericModal';
import EditRiskModal from './Components/EditRiskModal';


class Riesgos extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            showDel: false,
            showEdit: false,
            editRisk: null,
            delId: "",
            sortingValue: 'pk_id',
            sortingWay: 'desc',
            riesgos: []
        };
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updateRiesgos = this.updateRiesgos.bind(this);
        this.updateRiesgosBySearch = this.updateRiesgosBySearch.bind(this);
        this.updateRiesgosSort = this.updateRiesgosSort.bind(this);
        this.deleteRisk = this.deleteRisk.bind(this);
        this.openModalDelete = this.openModalDelete.bind(this);
        this.closeModalDelete = this.closeModalDelete.bind(this);
        this.openModalEdit = this.openModalEdit.bind(this);
        this.closeModalEdit = this.closeModalEdit.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
        this.handleSortSelect = this.handleSortSelect.bind(this);
        this.handleSortClick = this.handleSortClick.bind(this);
    }
    //On load
    componentDidMount() {
        this.refreshPage();
    }

    refreshPage() {
        let options = {
            url: process.env.REACT_APP_API_URL + "/RiskServlet",
            method: "GET",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }
        axios(options).then(response => {
            this.setState({ riesgos: response.data })
        }).catch((error) => {
            console.error(error.message);
        });
    }

    updateRiesgos(type) {
        if (type === "add-success") {
            toast.success("El Riesgo ha sido agregado satisfactoriamente!", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 5000
            });
        }
        let options = {
            url: process.env.REACT_APP_API_URL + "/RiskServlet",
            method: "GET",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
        axios(options).then(response => {
            this.setState({ riesgos: response.data })
        }).catch((error) => {
            console.error(error.message);
        });

    };

    updateRiesgosBySearch(type) {
        this.setState({ riesgos: type });
    }

    openModal = () => {
        this.setState({ show: true });
    };

    closeModal = () => {
        this.setState({ show: false });
    };

    updateRiesgosSort() {
        let sortingValue = this.state.sortingValue;
        let sortingWay = this.state.sortingWay;
        let options = {
            /*cambiar el link*/
            url: process.env.REACT_APP_API_URL + "/RetrieveRisks",
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
        axios(options).then(response => {
            this.setState({ riesgos: response.data })
        });

    };

    openModalEdit = (id) => {
        let risk = this.state.riesgos.find(risk => risk.id === id);
        this.setState({
            editRisk: risk,
            showEdit: true
        });

    };

    closeModalEdit = () => {
        this.setState({ showEdit: false, editRisk: null });
    };

    openModalDelete = (id) => {
        this.setState({ showDel: true, delId: id });
    };

    closeModalDelete = () => {
        this.setState({ showDel: false, delId: "" });
    };

    deleteRisk() {
        let options = {
            url: process.env.REACT_APP_API_URL + `/RiskManager/delete`,
            method: 'DELETE',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'id': this.state.delId
            }
        }
        axios(options)
            .then(response => {
                window.location.reload(false);
            })
    }

    handleSortSelect = e => {
        console.log(e.target.value)
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
                            <option selected disabled>Ordenar por...</option>
                            <option value='pk_id' defaultValue>ID</option>
                            <option value='name'>Nombre</option>
                            <option value='generalType'>Tipo General</option>
                            <option value='areaType'>Tipo por Área</option>
                            <option value='specType'>Tipo Específico</option>
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
                            <option value='pk_id'>ID</option>
                            <option value='name'>Nombre</option>
                            <option value='entryDate' defaultValue>Fecha de Ingreso</option>
                            <option value='status'>Estado</option>
                            <option value='authorName'>Autor</option>
                            <option value='type'>Tipo</option>
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
                        riesgos={this.state.riesgos}
                        updateRiesgosSort={this.updateRiesgosSort}
                        openModalDelete={this.openModalDelete}
                        openModalEdit={this.openModalEdit}
                    />
                </Row>
                <EditRiskModal
                    refreshPage={this.refreshPage}
                    risk={this.state.editRisk}
                    show={this.state.showEdit}
                    closeModalEdit={this.closeModalEdit}
                />
                <AddRiskModal
                    show={this.state.show}
                    updateRiesgos={this.updateRiesgos}
                    closeModal={this.closeModal} />
                <ToastContainer />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelete}
                    action={this.deleteRisk}
                    header={"Eliminar Riegos"}
                    body={"¿Desea eliminar este riesgo? Una vez eliminado no se podra recuperar el riesgo seleccionado"} />
            </div>
        );
    }
};
export default Riesgos;