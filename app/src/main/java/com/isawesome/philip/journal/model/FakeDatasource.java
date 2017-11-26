package com.isawesome.philip.journal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Philip on 11/23/2017.
 */

public class FakeDatasource
{

    public void create(JournalEntry entry)
    {
        List<JournalEntry> entries = createSampleEntries();
        entries.add(entry);
    }


    public List<JournalEntry> read()
    {
        return createSampleEntries();
    }


    public void update(JournalEntry updatedEntry)
    {
        List<JournalEntry> entries = createSampleEntries();
        entries.set(entries.size() - 1, updatedEntry);
    }



    private List<JournalEntry> createSampleEntries()
    {
        ArrayList<JournalEntry> entries = new ArrayList<>();
        String [] messages = new String[] {
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer at erat consequat leo mattis tristique a ac ex. Pellentesque dolor ipsum, aliquet eget nunc in, pharetra commodo orci. Etiam fermentum sodales gravida. Donec commodo sem lacus. Maecenas porta ipsum massa, sed consequat augue consectetur ut. Suspendisse ut quam ligula. Suspendisse laoreet, ante a maximus accumsan, felis nulla bibendum diam, ac pharetra eros arcu vitae purus. Vivamus quis aliquet odio, pellentesque ultricies eros. Sed tempus rutrum pharetra. Morbi eu sollicitudin sem. Donec pretium dui metus, ut placerat enim tristique vitae. Duis mattis sit amet massa eget lobortis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer tincidunt odio sed orci dignissim pharetra. Mauris interdum magna vitae diam vehicula gravida.",
                "Fusce dignissim massa ac justo gravida, sit amet semper augue consequat. Morbi dapibus sed leo eu tincidunt. Proin ut condimentum felis. Sed sit amet enim tortor. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque cursus egestas venenatis. Fusce cursus sodales elit eget bibendum. Proin et nunc fringilla, interdum sapien id, hendrerit purus. Sed pharetra, ipsum ac tincidunt malesuada, nunc justo ornare tortor, eget porttitor neque risus et felis. Quisque pellentesque vulputate tortor, in ornare elit venenatis eget. Curabitur pharetra, lacus vel volutpat lacinia, massa dui imperdiet elit, sit amet sagittis libero ante in quam. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Quisque turpis ante, molestie at risus eu, sodales tempor risus. Etiam maximus venenatis ultrices. sients stritsr tsritsrientsrntuyf f ientsrien strunfeinftien tun tsau yfenftneitfien ftun uy tsyun tsr uyst yutn suynstunstfnfn n feinie feni stu ysu ynrstun ysrt unytrsunstrnuysrt nuystrn uyrstn yutsr nuysntruyuyrt ffne fn eifn ifn ienifn ie fnie  nifien ien fnie en ifien fienfyu syu suy snuy sunysunysnyu sn yusuyn syun syun snyu syun snyu syun uyn tsrie ein ien n ietsein tsien tsrein",
                "Nam sit amet aliquam dolor, id ornare odio. Vestibulum vitae libero lectus. Fusce commodo, risus eu fermentum placerat, elit ipsum mollis eros, at placerat purus sem sed mi. Integer quis eros vestibulum, sagittis magna in, luctus tellus. Donec et turpis orci. Curabitur egestas sapien nisi, vel consequat purus vulputate ac. In aliquet vel est id tempor. Vivamus ac felis vitae enim egestas lobortis. Suspendisse rutrum, est vel sodales luctus, ex justo dictum risus, sed malesuada dolor diam a massa. Phasellus scelerisque neque vitae felis pellentesque ornare. Nullam nec elit et nulla ornare lacinia non eu orci. Morbi ante eros, consequat ac mollis et, pulvinar eu libero. Vivamus tempor hendrerit aliquam. Mauris non suscipit sem. Curabitur hendrerit odio at malesuada malesuada.",
                "Etiam vitae accumsan nulla. Integer semper, elit eu aliquam viverra, ex velit dictum dui, in tempor odio metus fringilla lorem. Nullam laoreet non lectus sed consequat. Maecenas fermentum enim et erat varius, et porta eros semper. Suspendisse lacinia diam sem, in interdum lorem suscipit eu. Morbi hendrerit fermentum lacus, ac feugiat leo feugiat quis. Donec orci neque, tempus eu egestas id, suscipit in ligula. Duis felis neque, luctus at enim sed, condimentum semper est. Praesent velit orci, commodo eu congue vitae, tincidunt sed eros. Maecenas vehicula lacus ultricies, congue ex nec, rutrum augue.",
                "Aliquam tortor neque, placerat eu luctus auctor, porttitor ut orci. Aliquam metus diam, rhoncus sed efficitur sed, finibus vel urna. Curabitur non lacus vestibulum, cursus libero eu, euismod orci. Suspendisse nec ex scelerisque, rutrum lectus eu, eleifend ante. Morbi ultricies ipsum eu urna lacinia sollicitudin. Sed eget nisi congue, commodo nunc ac, tempor arcu. Sed ac consequat velit. Nunc eleifend fringilla condimentum. Fusce ac finibus eros. In vestibulum, lorem ac laoreet venenatis, mi nunc blandit sapien, vitae tempus sem orci at sapien. Duis tortor purus, euismod ut neque eu, gravida porttitor magna. Nullam pellentesque eu lacus consectetur efficitur. Ut mauris erat, ultricies vitae blandit egestas, rhoncus vitae odio. Nunc facilisis turpis vitae ante tristique, in viverra sapien scelerisque. Proin non velit nec magna efficitur blandit ac non mi."
        };
        String [] dates = new String[] {
                "2017-10-21-Sat",
                "2017-10-22-Sun",
                "2017-10-23-Mon",
                "2017-10-24-Tue",
                "2017-10-25-Wed",
                "2017-10-26-Thu",
                "2017-10-27-Fri",
                "2017-10-28-Sat",
                "2017-10-29-Sun",
                "2017-10-30-Mon",
                "2017-10-31-Tue",
                "2017-11-01-Wed",
                "2017-11-02-Thu",
                "2017-11-03-Fri",
                "2017-11-04-Sat",
                "2017-11-05-Sun",
                "2017-11-06-Mon",
                "2017-11-07-Tue",
                "2017-11-08-Wed",
                "2017-11-09-Thu",
                "2017-11-10-Fri",
                "2017-11-11-Sat",
                "2017-11-12-Sun",
                "2017-11-13-Mon",
                "2017-11-14-Tue",
                "2017-11-15-Wed",
                "2017-11-16-Thu",
                "2017-11-17-Fri",
                "2017-11-18-Sat",
                "2017-11-19-Sun"
        };


        for (int i = 0; i < dates.length; i++)
        {
            String message = messages[(int)(Math.random()*5)];

            //entries.add(new JournalEntry((Long) i, message, dates[i]));
        }

        return entries;
    }
}
