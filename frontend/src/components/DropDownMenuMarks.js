import { DropDownList } from "@progress/kendo-react-dropdowns";

export const DropDownMenuMarks = ({ values, onChange }) => {
    return (
        <section className="k-my-8">
            <form className="k-form">
                <DropDownList data={values} textField="mark" dataItemKey="id" onChange={e => onChange(e.value)} />
            </form>
        </section>
    );
};

export const DropDownMenuAbsences = ({ values, onChange }) => {
    return (
        <section className="k-my-8">
            <form className="k-form">
                <DropDownList data={values} textField="date" dataItemKey="id" onChange={e => onChange(e.value)} />
            </form>
        </section>
    );
};