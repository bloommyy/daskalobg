import Nav from '../components/TeacherAppNavBar';
import { Button, Form, TeacherButton } from '../components/HomePageCSS';
import { GetTable } from '../components/Table';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { AddNewAbsence, AddNewMark, AddNewFeedback, RemoveMark, RemoveAbsence, RemoveFeedback, ExcuseAbsence } from '../components/TeacherComponents';
import { TableType } from '../utils'

export function TeacherHomePage() {
    const [grades, setGrades] = useState(false);
    const [absences, setAbsences] = useState(false);
    const [feedbacks, setFeedback] = useState(false);
    const userJSON = JSON.parse(localStorage.getItem('user'));

    const [gradesData, setGradesData] = useState([]);
    const [absencesData, setAbsencesData] = useState('');
    const [feedbacksData, setFeedbacksData] = useState('');
    const [classes, setClasses] = useState([]);
    const [selClass, setSelClass] = useState('');

    const [students, setStudents] = useState([]);

    const [addNewMark, setAddNewMark] = useState(false);
    const [addNewAbsence, setAddNewAbsence] = useState(false);
    const [excuseAbsence, setExcuseAbsence] = useState(false);
    const [addNewFeedback, setAddNewFeedback] = useState(false);

    const [removeMark, setRemoveMark] = useState(false);
    const [removeAbsence, setRemoveAbsence] = useState(false);
    const [removeFeedback, setRemoveFeedback] = useState(false);

    var selectedClass = '';

    useEffect(() => {
        if (classes.length === 0)
            axios.get('http://localhost:8080/teacher/classes?teacherId=' + userJSON.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined')
                        return;

                    setClasses(response.data);
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    })

    function onChange(item) {
        selectedClass = item
        setSelClass(item)
        onGrades()
    }

    function onGrades() {
        if (selectedClass !== '' || selClass !== '')
            axios.get('http://localhost:8080/student/marks/byClassAndSubject?stClass=' + (selectedClass !== '' ? selectedClass : selClass) + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined')
                        return;

                    setGradesData(response.data)
                    setGrades(false);
                    setAbsences(false);
                    setFeedback(false);
                    setGrades(true)
                })
                .catch(function (error) {
                    console.log(error)
                    alert(error.response.data)
                    return;
                })
    }

    function onAbsences() {
        if (selClass !== '')
            axios.get('http://localhost:8080/student/absences/byClassAndSubject?stClass=' + selClass + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined')
                        return;

                    setAbsencesData(response.data);
                    setAbsences(false);
                    setGrades(false);
                    setFeedback(false);
                    setAbsences(true);
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onFeedback() {
        if (selClass !== '')
            axios.get('http://localhost:8080/student/feedbacks/byClassAndSubject?stClass=' + selClass + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    setFeedbacksData(response.data);
                    setGrades(false);
                    setAbsences(false);
                    setFeedback(false);
                    setFeedback(true);
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }


    function onAddNewMark() {
        if (selectedClass !== '' || selClass !== '')
            axios.get('http://localhost:8080/student/nameByClass?stClass=' + (selectedClass !== '' ? selectedClass : selClass))
                .then(function (response) {
                    let tempStudents = [];
                    response.data.map(function (item, index, array) {
                        tempStudents.push({
                            id: item.id,
                            names: item.firstName + " " + item.middleName + " " + item.lastName
                        })
                    })

                    setStudents(tempStudents);
                    setAddNewMark(!addNewMark)
                    setAddNewAbsence(false)
                    setExcuseAbsence(false)
                    setAddNewFeedback(false)
                    setRemoveMark(false)
                    setRemoveAbsence(false)
                    setRemoveFeedback(false)
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onAddNewAbsence() {
        if (selectedClass !== '' || selClass !== '') {
            axios.get('http://localhost:8080/student/nameByClass?stClass=' + (selectedClass !== '' ? selectedClass : selClass))
                .then(function (response) {
                    let tempStudents = [];
                    response.data.map(function (item, index, array) {
                        tempStudents.push({
                            id: item.id,
                            names: item.firstName + " " + item.middleName + " " + item.lastName
                        })
                    })

                    setStudents(tempStudents);
                    setAddNewMark(false)
                    setAddNewAbsence(!addNewAbsence)
                    setExcuseAbsence(false)
                    setAddNewFeedback(false)
                    setRemoveMark(false)
                    setRemoveAbsence(false)
                    setRemoveFeedback(false)
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
        }
    }

    function onExcuseAbsence() {
        if (selClass !== '')
            axios.get('http://localhost:8080/student/absences/byClassAndSubject?stClass=' + selClass + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined')
                        return;

                    setAbsencesData(response.data);
                    setAddNewMark(false)
                    setAddNewAbsence(false)
                    setExcuseAbsence(!excuseAbsence)
                    setAddNewFeedback(false)
                    setRemoveMark(false)
                    setRemoveAbsence(false)
                    setRemoveFeedback(false)
                    if (!removeAbsence) {
                        setGrades(false)
                        setAbsences(true)
                        setFeedback(false)
                        onAbsences()
                    }
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onAddNewFeedback() {
        if (selectedClass !== '' || selClass !== '')
            axios.get('http://localhost:8080/student/nameByClass?stClass=' + (selectedClass !== '' ? selectedClass : selClass))
                .then(function (response) {
                    let tempStudents = [];
                    response.data.map(function (item, index, array) {
                        tempStudents.push({
                            id: item.id,
                            names: item.firstName + " " + item.middleName + " " + item.lastName
                        })
                    })

                    setStudents(tempStudents);
                    setAddNewMark(false)
                    setAddNewAbsence(false)
                    setExcuseAbsence(false)
                    setAddNewFeedback(!addNewFeedback)
                    setRemoveMark(false)
                    setRemoveAbsence(false)
                    setRemoveFeedback(false)
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onRemoveMark() {
        if (selectedClass !== '' || selClass !== '')
            axios.get('http://localhost:8080/student/nameByClass?stClass=' + (selectedClass !== '' ? selectedClass : selClass))
                .then(function (response) {
                    let tempStudents = [];
                    response.data.map(function (item, index, array) {
                        tempStudents.push({
                            id: item.id,
                            names: item.firstName + " " + item.middleName + " " + item.lastName
                        })
                    })

                    setStudents(tempStudents)
                    setAddNewMark(false)
                    setAddNewAbsence(false)
                    setExcuseAbsence(false)
                    setAddNewFeedback(false)
                    setRemoveMark(!removeMark)
                    setRemoveAbsence(false)
                    setRemoveFeedback(false)
                    if (!removeMark) {
                        setGrades(true)
                        setAbsences(false)
                        setFeedback(false)
                        onGrades()
                    }
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onRemoveAbsence() {
        if (selClass !== '')
            axios.get('http://localhost:8080/student/absences/byClassAndSubject?stClass=' + selClass + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined')
                        return;

                    setAbsencesData(response.data);
                    setAddNewMark(false)
                    setAddNewAbsence(false)
                    setExcuseAbsence(false)
                    setAddNewFeedback(false)
                    setRemoveMark(false)
                    setRemoveAbsence(!removeAbsence)
                    setRemoveFeedback(false)
                    if (!removeAbsence) {
                        setGrades(false)
                        setAbsences(true)
                        setFeedback(false)
                        onAbsences()
                    }
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onRemoveFeedback() {
        if (selClass !== '')
            axios.get('http://localhost:8080/student/feedbacks/byClassAndSubject?stClass=' + selClass + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined') {
                        alert("Нещо се обърка.")
                        return;
                    }

                    setFeedbacksData(response.data)
                    setAddNewMark(false)
                    setAddNewAbsence(false)
                    setExcuseAbsence(false)
                    setAddNewFeedback(false)
                    setRemoveMark(false)
                    setRemoveAbsence(false)
                    setRemoveFeedback(!removeFeedback)
                    if (!removeFeedback) {
                        setGrades(false)
                        setAbsences(false)
                        setFeedback(true)
                        onFeedback()
                    }
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    return (
        <Form>
            <Nav classes={classes} selectedClassChanged={onChange} />
            <Button onClick={onGrades} width='32.5%' selected={grades}>Оценки</Button>
            <Button onClick={onAbsences} width='32.5%' selected={absences}>Отсъствия</Button>
            <Button onClick={onFeedback} width='32.5%' selected={feedbacks}>Забележки</Button>
            <TeacherButton isForAdding={true} selected={addNewMark} onClick={onAddNewMark} width='32.5%'>Добавете Оценка</TeacherButton>
            <TeacherButton isForAdding={true} selected={addNewAbsence} onClick={onAddNewAbsence} width='15.85%'>Добавете Отсъствие</TeacherButton>
            <TeacherButton isForAdding={true} selected={excuseAbsence} onClick={onExcuseAbsence} width='15.85%'>Извинете Отсъствие</TeacherButton>
            <TeacherButton isForAdding={true} selected={addNewFeedback} onClick={onAddNewFeedback} width='32.5%'>Добавете Забележка</TeacherButton>
            <TeacherButton isForAdding={false} selected={removeMark} onClick={onRemoveMark} width='32.5%'>Премахнете Оценка</TeacherButton>
            <TeacherButton isForAdding={false} selected={removeAbsence} onClick={onRemoveAbsence} width='32.5%'>Премахнете Отсъствие</TeacherButton>
            <TeacherButton isForAdding={false} selected={removeFeedback} onClick={onRemoveFeedback} width='32.5%'>Премахнете Забележка</TeacherButton>
            {
                removeMark && <RemoveMark updateData={(data) => setGradesData(data)} students={students} grades={gradesData} />
            }
            {
                removeAbsence && <RemoveAbsence updateData={(data) => setAbsencesData(data)} students={students} absences={absencesData} />
            }
            {
                excuseAbsence && <ExcuseAbsence updateData={(data) => setAbsencesData(data)} students={students} absences={absencesData} />
            }
            {
                removeFeedback && <RemoveFeedback updateData={(data) => setFeedbacksData(data)} feedbacksData={feedbacksData} />
            }
            {
                addNewMark && <AddNewMark updateData={(data) => setGradesData(data)} students={students} />
            }
            {
                addNewAbsence && <AddNewAbsence updateData={(data) => setAbsencesData(data)} students={students} />
            }
            {
                addNewFeedback && <AddNewFeedback updateData={(data) => setFeedbacksData(data)} students={students} />
            }
            {
                grades && <GetTable prepData={gradesData} type={TableType.Grades} />
            }
            {
                absences && <GetTable prepData={absencesData} type={TableType.Absences} />
            }
            {
                feedbacks && <GetTable prepData={feedbacksData} type={TableType.Feedbacks} />
            }
        </Form>
    )
}