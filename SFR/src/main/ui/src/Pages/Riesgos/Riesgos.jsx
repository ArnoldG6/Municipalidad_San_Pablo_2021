//import { render } from '@testing-library/react';
import React, { Component } from 'react';
import './Riesgos.css';
import { Button, Stack, Row } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AddRiskModal from './Components/AddRiskModal';
import axios from 'axios';
import RisksTable from './Components/RisksTable';
import Search from './Components/Search';
import GenericModal from '../../SharedComponents/GenericModal/GenericModal';

class Riesgos extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            showDel: false,
            delId: "",
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
    }
    //On load
    componentDidMount() {
        let options = {
            url: process.env.REACT_APP_API_URL + "/RiskServlet",
            method: "GET",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }
        //axios(options).then(toast.promise({pending: 'Cargando...'})).then(response => {
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
                autoClose: 10000
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
            //console.log(response.data)
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

    updateRiesgosSort(sortingValue, sortingWay) {

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

    render() {
        return (
            <div className="Riesgos-Container container-fluid">
                <Row className="mt-2">
                    <Stack direction="horizontal" gap={3}>
                        <Button className="btn-sfr" id="NewItemButton" size="sm" onClick={this.openModal}>Crear Item</Button>
                        <Search updateRiesgos={this.updateRiesgosBySearch} />
                    </Stack>
                </Row>
                <Row>
                    <RisksTable riesgos={this.state.riesgos} updateRiesgosSort={this.updateRiesgosSort} openModalDelete={this.openModalDelete}/>
                </Row>
                <AddRiskModal updateRiesgos={this.updateRiesgos} show={this.state.show} closeModal={this.closeModal} />
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