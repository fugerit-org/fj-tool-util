package org.fugerit.java.tool.util;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class ParamHolder {

    public static final int MODE_NAME = 0;

    public static final int MODE_AND = 1;

    public static final int MODE_OR = 2;

    @Getter
    private int mode = MODE_NAME;

    @Getter
    private String name;

    private List<ParamHolder> params = new ArrayList<>();

    public static ParamHolder newHolder( String name ) {
        ParamHolder holder = new ParamHolder();
        holder.name = name;
        return holder;
    }

    public static ParamHolder newHolder( int mode, String... names ) {
        return newHolder( mode, Arrays.stream(names).map( ParamHolder::newHolder ).collect( Collectors.toList() ).toArray( new ParamHolder[0] ) );
    }

    public static ParamHolder newHolder( int mode, ParamHolder... holders ) {
        ParamHolder holder = new ParamHolder();
        holder.mode = mode;
        holder.params.addAll(Arrays.asList( holders ));
        return holder;
    }

    public static ParamHolder newAndHolder(String... names) {
        return newHolder( MODE_AND, names );
    }

    public static ParamHolder newOrHolder( String... names) {
        return newHolder( MODE_OR, names );
    }

    public static ParamHolder newAndHolder(ParamHolder... holders) {
        return newHolder( MODE_AND, holders );
    }

    public static ParamHolder newOrHolder(ParamHolder... holders) {
        return newHolder( MODE_OR, holders );
    }

    public Collection<ParamHolder> getParams() {
        return Collections.unmodifiableCollection( this.params );
    }

    public boolean isAndHolder() {
        return this.mode == MODE_AND;
    }

    public boolean isOrHolder() {
        return this.mode == MODE_OR;
    }

    public boolean isNameHolder() {
        return this.mode == MODE_NAME;
    }

}
